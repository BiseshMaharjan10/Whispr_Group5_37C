package Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import Model.MessageModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.ClientGui;
import view.Profile;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String clientUsername;
    private ClientGui client;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            this.clientUsername = (String) in.readObject(); // receive username
            clientHandlers.add(this);
            MessageModel joinMsg = new MessageModel();
            joinMsg.setSender("SERVER");
            joinMsg.setMessage(clientUsername + " has entered the chat!");
            broadcastMessage(joinMsg);
            sendOnlineUsers();
        } catch (IOException | ClassNotFoundException e) {
            closeEverything(socket, in, out);
        }
        
        
    }
    
    public ClientHandler(ClientGui client){
        this.client = client;
        client.addProfileListener(new ProfileListener());
    }
    
    class ProfileListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Profile pp = new Profile();
            ProfileController controller = new ProfileController(pp);
            controller.openProfile();
        }
        
    }

    
    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                Object obj = in.readObject();

                if (obj instanceof MessageModel) {
                    MessageModel msg = (MessageModel) obj;

                    // Broadcast to others or handle private messaging if needed
                    broadcastMessage(msg);
                }

            } catch (IOException | ClassNotFoundException e) {
                closeEverything(socket, in, out);
                break;
            }
        }
    }
    

    public void broadcastMessage(MessageModel messageToSend) {
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
        MessageModel joinMsg = new MessageModel();
        joinMsg.setSender("SERVER");
        joinMsg.setMessage(clientUsername + " left the chat!");
        broadcastMessage(joinMsg);
        
        
        
          sendOnlineUsers();
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
    
    public void sendOnlineUsers() {
        ArrayList<String> onlineUsernames = new ArrayList<>();
        for (ClientHandler handler : clientHandlers) {
            onlineUsernames.add(handler.clientUsername);
        }

        for (ClientHandler handler : clientHandlers) {
            try {
                MessageModel userListMsg = new MessageModel();
                userListMsg.setSender("SERVER");
                userListMsg.setMessage("" + String.join(",", onlineUsernames));
                handler.out.writeObject(userListMsg);
                handler.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}