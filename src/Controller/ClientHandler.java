package Controller;

import Dao.ChatClientDAO;
import Model.MessageModel;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String clientUsername;
    
    public ClientHandler(){
        
    }
    
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
    
    
    public boolean sendPrivateMessage(MessageModel msg) {
        ChatClientDAO temp_obj = new ChatClientDAO();
        String first_n_lastName = temp_obj.getFirstnLastName(msg.getReceiver());

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.clientUsername.equals(first_n_lastName)) {
                try {
                    clientHandler.out.writeObject(msg);
                    clientHandler.out.flush();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return false; 
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
                userListMsg.setReceiver(handler.clientUsername); 
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
                


                if (obj instanceof MessageModel) {
                    MessageModel msg = (MessageModel) obj;
                    
                    
                    System.out.println("\nDEBUG: Received message from: " + msg.getSender() + 
                   ", to: " + msg.getReceiver() + 
                   ", message: " + msg.getMessage());

                    if (msg.getReceiver() != null && !msg.getReceiver().trim().isEmpty()) {
                        boolean delivered = sendPrivateMessage(msg);

                        if (!delivered) {
                            System.out.println("User not online or invalid email. Private message not sent.");

                            // Optional: notify sender
                            try {
                                MessageModel errorMsg = new MessageModel();
                                errorMsg.setSender("SERVER");
                                errorMsg.setMessage("User not found or offline. Message not delivered.");
                                out.writeObject(errorMsg);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        System.out.println("No receiver provided. Skipping message broadcast.");
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                closeEverything(socket, in, out);
                break;
            }
        }
    }
    
    
}