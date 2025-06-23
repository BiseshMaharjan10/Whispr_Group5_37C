package Model;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String sender;
    private String message;
    private String status; // e.g., SENT, DELIVERED, SEEN

    public Message() {}

    public Message(int id, String firstName, String lastName, String sender, String message, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sender = sender;
        this.message = message;
        this.status = status;
    }
        // New constructor for sender, recipient(full name), and message text
    public Message(String sender, String recipient, String message, String status) {
        this.sender = sender;
        String[] names = recipient.split(" ", 2);
        if (names.length == 2) {
            this.firstName = names[0];
            this.lastName = names[1];
        } else {
            this.firstName = recipient;
            this.lastName = "";
        }
        this.message = message;
        this.status = status;
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

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Convenience method for full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        // For example: "John Doe: Hello!"
        return getFullName() + ": " + message;
    }


}