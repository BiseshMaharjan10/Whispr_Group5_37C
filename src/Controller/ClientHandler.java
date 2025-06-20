package Controller;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import Model.Message;

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
            Message joinMsg = new Message();
            joinMsg.setSender("SERVER");
            joinMsg.setMessage(clientUsername + " has entered the chat!");
            broadcastMessage(joinMsg);
            sendOnlineUsers();
        } catch (IOException | ClassNotFoundException e) {
            closeEverything(socket, in, out);
        }
    }

    

    

    public void broadcastMessage(Message messageToSend) {
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
        Message joinMsg = new Message();
        joinMsg.setSender("SERVER");
        joinMsg.setMessage(clientUsername + " left the chat!");
        broadcastMessage(joinMsg);
        
        
        
          sendOnlineUsers();
    }
    
    
    public void sendPrivateMessage(Message msg) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.clientUsername.equals(msg.getReceiver())) {
                try {
                    clientHandler.out.writeObject(msg);
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
                Message userListMsg = new Message();
                userListMsg.setSender("SERVER");
                userListMsg.setMessage("" + String.join(",", onlineUsernames));
                handler.out.writeObject(userListMsg);
                handler.out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    
    
        @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                Object obj = in.readObject();
                


                if (obj instanceof Message) {
                    Message msg = (Message) obj;
                    
                    System.out.println("\nDEBUG: Received message from: " + msg.getSender() + 
                   ", to: " + msg.getReceiver() + 
                   ", message: " + msg.getMessage());

                    if (msg.getReceiver() != null && !msg.getReceiver().trim().isEmpty()) {
                        String a = msg.getReceiver();
                         System.out.println("user found online : \"" + a + "\"");
                        sendPrivateMessage(msg);
                        
                    } else {
                        System.out.println("user not found sending message in group");
                        broadcastMessage(msg); // Optional, for group message
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                closeEverything(socket, in, out);
                break;
            }
        }
    }
    
    
}