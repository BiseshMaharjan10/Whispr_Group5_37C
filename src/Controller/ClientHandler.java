package Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            this.clientUsername = (String) in.readObject(); // receive username
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException | ClassNotFoundException e) {
            closeEverything(socket, in, out);
        }
    }

    
    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String messageFromClient = (String) in.readObject();

                if (messageFromClient.startsWith("TO::")) {
                    String[] parts = messageFromClient.split("::", 3);
                    if (parts.length == 3) {
                        String recipient = parts[1];
                        String message = parts[2];
                        sendPrivateMessage(recipient, message);
                    }
                } else {
                    broadcastMessage(clientUsername + ": " + messageFromClient);
                }
            } catch (IOException | ClassNotFoundException e) {
                closeEverything(socket, in, out);
                break;
            }
        }
    }
    

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.out.writeObject(messageToSend);
                    clientHandler.out.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, in, out);
            }
        }
    }

    public void closeEverything(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat.");
    }
    
    
    
    public void sendPrivateMessage(String recipientUsername, String message) {
    for (ClientHandler clientHandler : clientHandlers) {
        if (clientHandler.clientUsername.equals(recipientUsername)) {
            try {
                clientHandler.out.writeObject(clientUsername + ": " + message);
                clientHandler.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
    }
}
}