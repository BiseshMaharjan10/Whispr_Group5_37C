package Controller;

import Dao.ChatClientDAO;
import Model.MessageModel;
import view.ClientGui;


import javax.swing.*;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class ChatController implements ActionListener {
    private final ChatClientDAO chatClientDAO;
    private final JList<String> contactList;
    private final JTextField messageInput;
    private final JPanel messagePanel;
    private final JScrollPane messageScroll;
    private final JTextField searchField;
    private final JPanel bottomPanel;
    private final JLabel imageLabel;
    private final String currentUserName;
    private final ClientGui userView;

    private final Map<String, List<JLabel>> chatHistory = new HashMap<>();
    private final Box.Filler bottomFiller = new Box.Filler(
        new Dimension(0, 0), new Dimension(0, 0), new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ChatController(ClientGui userView) {
        this.userView = userView;
        
        this.chatClientDAO = new ChatClientDAO();
        this.contactList = userView.getContactList();
        this.messageInput = userView.getMessageInput();
        this.messagePanel = userView.getMessagePanel();
        this.messageScroll = userView.getMessageScroll();
        this.searchField = userView.getSearchField();
        this.bottomPanel = userView.getBottomPanel();
        this.imageLabel = userView.getImageLabel();
        this.currentUserName = userView.getCurrentUsername();

        initializeConnection();
    }

    private void initializeConnection() {
        try {
            socket = new Socket("127.0.0.1", 1234);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(currentUserName);
            out.flush();
    
            new Thread(() -> listenForMessages()).start();
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot connect to the server.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listenForMessages() {
        while (socket.isConnected()) {
            try {
                    
                Object obj = in.readObject();
                if (obj instanceof MessageModel msg) {
                    
                    handleIncomingMessage(msg);
                }
  
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void handleIncomingMessage(MessageModel msg) {
        if ("SERVER".equals(msg.getSender()) && msg.getMessage().contains(",")) {
            
            System.out.println("this is inside handleincomeing message if");
            updateContactList(msg.getMessage());
        } else if (contactList.getSelectedValue() != null) {
            System.out.println("this is inside handleincomeing message else");
            boolean isSelf = msg.getSender().equals(currentUserName);
            displayMessage(msg.getSender(), msg.getMessage(), isSelf);
        }
    }

    private void updateContactList(String csv) {
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            for (String user : csv.split(",")) {
                if (!user.equals(currentUserName)) model.addElement(user);
            }
            contactList.setModel(model);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String to = contactList.getSelectedValue();
        String text = messageInput.getText().trim();
        if (to != null && !text.isEmpty()) {
            messageInput.setText("");
            displayMessage("Me", text, true);
            String[] names = to.split(" ", 2);
            
            String email = chatClientDAO.getEmail(names[0], names.length > 1 ? names[1] : "");
            
            sendMessage(currentUserName, email, text);
        } else {
            JOptionPane.showMessageDialog(null, "Select contact & write message", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void displayMessage(String sender, String message, boolean isOwnMessage) {
        
        System.out.println("this is inside displayMessage");
        SwingUtilities.invokeLater(() -> {
            messagePanel.remove(bottomFiller);
            String bubbleColor = isOwnMessage ? "#DDFECD" : "#F0F0F0";
            String align = isOwnMessage ? "right" : "left";
            String timestamp = LocalTime.now().withSecond(0).withNano(0).toString();
            JLabel msgLabel = new JLabel("<html><div style='padding: 8px; background: " + bubbleColor +
                "; border-radius: 10px; max-width: 300px; text-align: " + align + ";'>" +
                message + "<br><span style='font-size: 10px; color: gray;'>" + timestamp + "</span></div></html>");

            chatHistory.computeIfAbsent(sender, k -> new ArrayList<>()).add(msgLabel);
            JPanel wrapper = new JPanel(new FlowLayout(isOwnMessage ? FlowLayout.RIGHT : FlowLayout.LEFT));
            wrapper.setOpaque(false);
            wrapper.add(msgLabel);
            messagePanel.add(wrapper);
            messagePanel.add(Box.createVerticalStrut(5));
            messagePanel.add(Box.createVerticalGlue());
            messagePanel.add(bottomFiller);
            messagePanel.revalidate();
            messagePanel.repaint();
            messageScroll.getVerticalScrollBar().setValue(messageScroll.getVerticalScrollBar().getMaximum());
        });
    }

    public void sendMessage(String from, String to, String text) {
        try {
            out.writeObject(new MessageModel(from, to, text));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to send message", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void highlightMessages() {
        String keyword = searchField.getText().trim();
        String contact = contactList.getSelectedValue();
        if (contact == null || keyword.isEmpty()) {
            showMessages(contact);
            return;
        }

        List<JLabel> original = chatHistory.getOrDefault(contact, new ArrayList<>());
        messagePanel.removeAll();
        for (JLabel msg : original) {
            String raw = msg.getText().replaceAll("<[^>]*>", "");
            JLabel highlighted = raw.contains(keyword)
                ? new JLabel("<html><div style='padding: 8px; background: yellow;'>" + raw + "</div></html>")
                : new JLabel(msg.getText());

            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            wrapper.setOpaque(false);
            wrapper.add(highlighted);
            messagePanel.add(wrapper);
            messagePanel.add(Box.createVerticalStrut(5));
        }
        messagePanel.revalidate();
        messagePanel.repaint();
    }

    public void showMessages(String contact) {
        messagePanel.removeAll();
        List<JLabel> messages = chatHistory.getOrDefault(contact, new ArrayList<>());
        for (JLabel label : messages) {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            wrapper.setOpaque(false);
            wrapper.add(label);
            messagePanel.add(wrapper);
            messagePanel.add(Box.createVerticalStrut(5));
        }
        messagePanel.add(Box.createVerticalGlue());
        messagePanel.add(bottomFiller);
        messagePanel.revalidate();
        messagePanel.repaint();
        messageScroll.getVerticalScrollBar().setValue(messageScroll.getVerticalScrollBar().getMaximum());
    }

    public void updateImage(String imagePath) {
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        imageLabel.setIcon(icon);
        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        for (MouseListener ml : imageLabel.getMouseListeners()) imageLabel.removeMouseListener(ml);

        imageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JLabel img = new JLabel(new ImageIcon(imagePath));
                JScrollPane scroll = new JScrollPane(img);
                scroll.setPreferredSize(new Dimension(400, 400));
                JOptionPane.showMessageDialog(null, scroll, "Full Image", JOptionPane.PLAIN_MESSAGE);
            } 
        });
    }
    
    
    public List<String> getAllUserFullNames() {
        
        ChatClientDAO getall = new ChatClientDAO();
        List<MessageModel> users = getall.getAllUsers();
        List<String> fullNames = new ArrayList<>();
        for (MessageModel user : users) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            fullNames.add(fullName);
        }
        return fullNames;
    }
    
    
    public ActionListener getSendActionListener() {
        return this;
    }
}
