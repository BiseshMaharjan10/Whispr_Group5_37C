package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String sender;
    private String receiver;
    private String imagePath;
    private String message;
    private String currentUserName;
    private String status;

    // Constructors
    public MessageModel() {
    }

    public MessageModel(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public MessageModel(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public MessageModel(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getMessage() {
        return message;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public String getStatus() {
        return status;
    }

    public String getSelectedUsername() {
        return currentUserName;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Convenience method: return "First Last"
    public String getFullName() {
        String fname = (firstName == null) ? "" : firstName;
        String lname = (lastName == null) ? "" : lastName;
        return fname + " " + lname;
    }

    // Utility: Get online users from CSV in `message`
    public List<String> getOnlineUsers() {
        List<String> csvUsers = new ArrayList<>();
        try {
            if (message == null) return csvUsers;
            for (String user : message.split(",")) {
                String trimmed = user.trim();
                if (!trimmed.isEmpty()) {
                    csvUsers.add(trimmed);
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing CSV users: " + e.getMessage());
        }
        return csvUsers;
    }

    // For debugging/logs
    @Override
    public String toString() {
        return getFullName() + ": " + message;
    }
}