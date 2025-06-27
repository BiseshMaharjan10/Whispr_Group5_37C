package Model;

import java.io.Serializable;

public class MessageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String sender;
    private String message;

    public MessageModel() {}

    public MessageModel(int id, String firstName, String lastName, String sender, String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sender = sender;
        this.message = message;
    }
        // New constructor for sender, recipient(full name), and message text
    public MessageModel(String sender, String recipient, String message) {
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