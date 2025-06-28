package Model;

import java.io.Serializable;

public class MessageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String sender;
    private String receiver;
    private String message;
    private String status; // e.g., SENT, DELIVERED, SEEN
    private String currentUserName;

    public MessageModel() {}

    public MessageModel(int id, String firstName, String lastName, String sender, String message, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sender = sender;
        this.message = message;
        this.status = status;
    }

    public MessageModel(int id, String firstName, String lastName, String sender, String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sender = sender;
        this.message = message;
    }

    public MessageModel(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public MessageModel(String selectedUserName) {
        this.currentUserName = selectedUserName;
    }

    // Getters
    public int getId() {
        return id;
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

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    // Convenience method for full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return getFullName() + ": " + message;
    }
}