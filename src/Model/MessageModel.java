package Model;

import Dao.ChatClientDAO;
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
    
    private ChatClientDAO dao;
    
    private List<String> allUsersInDb;
    private String firstnLastNameThroughemail;
    private String emailThroughFirstnLastName;
    private Boolean updateUserImagePath;
    private String getImagePath;
    private String message;
    private String currentUserName;
    private String status;
    
    
    

    public MessageModel() {}

    public MessageModel(int id, String fName, String lName, String sender, String message) {
        ChatClientDAO dao = new ChatClientDAO();
        this.firstnLastNameThroughemail = dao.getFirstnLastName(email);
        this.emailThroughFirstnLastName = dao.getEmail(firstName, lastName);
        this.updateUserImagePath = dao.updateUserImagePath(email, imagePath);
        this.getImagePath = dao.getImagePath(email);
        
        this.id = id;
        this.firstName = fName;
        this.lastName = lName;
        this.sender = sender;
        this.message = message;

        List<MessageModel> users = dao.getAllUsers();
        List<String> fullNames = new ArrayList<>();
        for (MessageModel user : users) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            fullNames.add(fullName);
            
            this.allUsersInDb = fullNames;

        }
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
//    public List<String> getAllUserFullName() {
//        return allUsersInDb;
//
//    }

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