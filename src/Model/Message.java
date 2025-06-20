package Model;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String sender;
    private String message;
    private String receiver;

    public Message() {}

    public Message(int id, String firstName, String lastName, String sender, String message) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sender = sender;
        this.message = message;

    }
        // New constructor for sender, recipient(full name), and message text
    public Message(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        

        
//        String[] names = receiver.split(" ", 2);
//        
//        if (names.length == 2) {
//            this.firstName = names[0];
//            this.lastName = names[1];
//        } else {
//            this.firstName = receiver;
//            this.lastName = "";
//        }

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
    public String getReceiver(){
        return receiver;
        
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
    
    public void setReceiver(String receiver) {
        this.receiver = receiver;
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