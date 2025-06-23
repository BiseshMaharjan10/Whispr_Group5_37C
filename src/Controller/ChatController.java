package Controller;

import Dao.ChatClientDAO;
import Model.Message;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Component;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;


public class ChatController implements ActionListener {
    private final ChatClientDAO userDAO;
    private final JList<String> contactList;
    private final JTextField messageInput;
    private final JPanel messagePanel;
    private final JScrollPane messageScroll;
    private final Map<String, List<JLabel>> chatHistory;
    private JTextField searchField; 
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String currentUserName;
    private JPanel bottompanel = new JPanel();

    public ChatController(ChatClientDAO userDAO, JList<String> contactList, JTextField messageInput,
                          JPanel messagePanel, JScrollPane messageScroll, String currentUserName, JTextField searchField,
                      JPanel bottompanel) {
        this.userDAO = userDAO;
        this.contactList = contactList;
        this.messageInput = messageInput;
        this.messagePanel = messagePanel;
        this.messageScroll = messageScroll;
        this.chatHistory = new HashMap<>();
        this.currentUserName = currentUserName;
        this.searchField = searchField;
        this.bottompanel = bottompanel;

        initializeConnection();
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

    private void initializeConnection() {
        try {
            socket = new Socket("127.0.0.1", 1234);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(currentUserName); // send username
            out.flush();

            new Thread(() -> {
                while (socket.isConnected()) {
                    try {
                        Object obj = in.readObject();

                        if (obj instanceof Message) {
                            Message msg = (Message) obj;
                            if ("SERVER".equals(msg.getSender()) && msg.getMessage().contains(",")) {
                                // Assume it's online user list
                                String[] users = msg.getMessage().split(",");
                                SwingUtilities.invokeLater(() -> {
                                    DefaultListModel<String> model = new DefaultListModel<>();
                                    for (String user : users) {
                                        if (!user.equals(currentUserName)) {
                                            model.addElement(user);
                                        }
                                    }
                                    contactList.setModel(model); // updates JList
                                });
                            } else {
                                // Handle normal messages
                                if (msg.getSender().equals(currentUserName)) {
                                    displayMessage("Me", msg.getMessage(), true);
                                } else {
                                    displayMessage(msg.getSender(), msg.getMessage(), false);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot connect to the server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedContact = contactList.getSelectedValue();
        String text = messageInput.getText().trim();

        if (!text.isEmpty() && selectedContact != null) {
            chatHistory.putIfAbsent(selectedContact, new ArrayList<>());

            String timestamp = LocalTime.now().withSecond(0).withNano(0).toString();
            JLabel messageLabel = new JLabel("<html><div style='padding: 8px; background: #DCF8C6; border-radius: 10px; max-width: 300px; text-align: right;'>" +
                    text + "<br><span style='font-size: 10px; color: gray;'>" + timestamp +
                    " <span style='color:#34B7F1;'>&#10003;</span></span></div></html>");

            JPanel wrapper = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
            wrapper.setOpaque(false);
            wrapper.add(messageLabel);

            chatHistory.get(selectedContact).add(messageLabel);
            messagePanel.add(wrapper, messagePanel.getComponentCount() - 1);
            messagePanel.add(Box.createVerticalStrut(5), messagePanel.getComponentCount() - 1);

            messagePanel.revalidate();
            messagePanel.repaint();
            messageInput.setText("");

            SwingUtilities.invokeLater(() -> {
                JScrollBar vertical = messageScroll.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            });

            sendMessage(currentUserName, selectedContact, text);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a contact and enter a message.", "Error", JOptionPane.WARNING_MESSAGE);
        }
        messageInput.setText("");
    }

    public void receiveMessage(Message msg) {
        String from = msg.getSender();
        String content = msg.getMessage();
        chatHistory.putIfAbsent(from, new ArrayList<>());

        String timestamp = LocalTime.now().withSecond(0).withNano(0).toString();
        JLabel messageLabel = new JLabel("<html><div style='padding: 8px; background: #FFF; border-radius: 10px; max-width: 300px; text-align: left;'>" +
                content + "<br><span style='font-size: 10px; color: gray;'>" + timestamp + "</span></div></html>");

        JPanel wrapper = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        wrapper.setOpaque(false);
        wrapper.add(messageLabel);

        chatHistory.get(from).add(messageLabel);
        messagePanel.add(wrapper, messagePanel.getComponentCount() - 1);
        messagePanel.add(Box.createVerticalStrut(5), messagePanel.getComponentCount() - 1);

        messagePanel.revalidate();
        messagePanel.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = messageScroll.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
    
    

    public void highlightMessages() {
        String keyword = searchField.getText();
        String selectedContact = contactList.getSelectedValue();

        if (selectedContact == null || keyword.isEmpty()) {
            showMessages(selectedContact, messagePanel);  // Show normal messages
            return;
        }

        java.util.List<JLabel> originalMessages = chatHistory.getOrDefault(selectedContact, new java.util.ArrayList<>());
        messagePanel.removeAll();

        boolean scrolled = false;

        for (int i = originalMessages.size() - 1; i >= 0; i--) {
            JLabel original = originalMessages.get(i);
            String text = original.getText();
            String plainText = text.replaceAll("<[^>]*>", ""); // remove HTML tags

            JLabel label;
            if (plainText.contains(keyword)) {
                // Highlight keyword
                String highlighted = plainText.replace(keyword, "<span style='background: yellow;'>" + keyword + "</span>");
                label = new JLabel("<html><div style='padding: 8px; background: #DCF8C6; border-radius: 10px; max-width: 300px; text-align: right;'>"
                        + highlighted + "</div></html>");

                if (!scrolled) {
                    final JLabel scrollTarget = original;
                    SwingUtilities.invokeLater(() -> scrollTarget.scrollRectToVisible(new Rectangle(scrollTarget.getBounds())));
                    scrolled = true;
                }
            } else {
                label = new JLabel(text); // unmodified
            }

            label.setAlignmentX(Component.RIGHT_ALIGNMENT);
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            wrapper.setOpaque(false);
            wrapper.add(label);
            messagePanel.add(wrapper);
            messagePanel.add(Box.createVerticalStrut(5));
        }
        messagePanel.revalidate();
        messagePanel.repaint();
    }


    
    
    
    
    public void sendMessage(String from, String to, String messageText) {
        if (out != null) {
            try {
                Message message = new Message(0, "", "", from, messageText, "SENT");
                out.writeObject(message);
                out.flush();
                System.out.println("Sent from " + from + " to " + to + ": " + messageText);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to send the message.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void showMessages(String contact, JPanel bottomPanel) {
        messagePanel.removeAll();
        List<JLabel> messages = chatHistory.getOrDefault(contact, new ArrayList<>());

        for (JLabel label : messages) {
            JPanel wrapper = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
            wrapper.setOpaque(false);
            wrapper.add(label);
            messagePanel.add(wrapper);
            messagePanel.add(Box.createVerticalStrut(5));
        }

//        messagePanel.add(Box.createVerticalGlue());
//        messagePanel.add(bottomPanel);

        messagePanel.revalidate();
        messagePanel.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = messageScroll.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
    
    
    public void displayMessage(String sender, String messageText, boolean isOwnMessage) {
        SwingUtilities.invokeLater(() -> {
            String timestamp = LocalTime.now().withSecond(0).withNano(0).toString();

            String bubbleColor = isOwnMessage ? "#DDFECD" : "#F0F0F0"; // light green vs light gray
            String align = isOwnMessage ? "right" : "left";
            String tick = isOwnMessage ? " <span style='color:#34B7F1;'>&#10003;&#10003;</span>" : ""; // double tick for sent messages

            JLabel messageLabel = new JLabel("<html><div style='padding: 8px; background: " + bubbleColor
                    + "; border-radius: 10px; max-width: 300px; text-align: " + align + ";'>"
                    + messageText + "<br><span style='font-size: 10px; color: gray;'>"
                    + timestamp + tick + "</span></div></html>");

            JPanel wrapper = new JPanel(new FlowLayout(isOwnMessage ? FlowLayout.RIGHT : FlowLayout.LEFT));
            wrapper.setOpaque(false);
            wrapper.add(messageLabel);

            messagePanel.add(wrapper, messagePanel.getComponentCount() - 1);
            messagePanel.add(Box.createVerticalStrut(5), messagePanel.getComponentCount() - 1);

            messagePanel.revalidate();
            messagePanel.repaint();

            JScrollBar vertical = messageScroll.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
}