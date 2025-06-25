package utils;

import view.ClientGui;
import Controller.ChatController;
import Dao.ChatClientDAO;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog("Enter your username:");
        if (username != null && !username.trim().isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                // Create GUI
                ClientGui gui = new ClientGui(username);
                
                // Create DAO
                ChatClientDAO dao = new ChatClientDAO();

                // Create Controller and wire its
                ChatController controller = new ChatController(
                    dao,
                    gui.getContactList(),
                    gui.getMessageInput(),
                    gui.getMessagePanel(),
                    gui.getMessageScroll(),
                    username,
                    gui.getSearchField(),
                    gui.getBottomPanel(),
                    gui.getImageLabel()
                );

                // Connect Listeners
                gui.addSendButtonListener(controller.getSendActionListener());
                gui.addMessageInputListener(controller.getSendActionListener());
                gui.addContactListSelectionListener(e -> {
                    String selected = gui.getSelectedContact();
                    if (selected != null) {
                        controller.showMessages(selected);
                        gui.getBottomPanel().setVisible(true);
                    }
                });
                gui.addSearchButtonListener(e -> gui.toggleSearchPanel(true));
                gui.addSearchFieldListener(new javax.swing.event.DocumentListener() {
                    @Override
                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        controller.highlightMessages();
                    }

                    @Override
                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        controller.highlightMessages();
                    }

                    @Override
                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        controller.highlightMessages();
                    }
                });

                // Show GUI
                gui.setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(null, "Username is required.");
            System.exit(0);
        }
    }
}