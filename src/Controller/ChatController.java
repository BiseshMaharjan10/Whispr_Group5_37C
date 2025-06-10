package Controller;

import Dao.ChatClient;
import Model.Message;
import java.util.ArrayList;
import java.util.List;

public class ChatController {
    private final ChatClient userDAO;

    public ChatController(ChatClient userDAO) {
        this.userDAO = userDAO;
    }

    public List<String> getAllUserFullNames() {
        List<Message> users = userDAO.getAllUsers();
        List<String> fullNames = new ArrayList<>();

        for (Message user : users) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            fullNames.add(fullName);
        }

        return fullNames;
    }
}