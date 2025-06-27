package Controller;

import Dao.ChatClientDAO;
import Model.MessageModel;
import view.ClientGui;
import Controller.SigninController;

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
import java.awt.image.BufferedImage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import view.RoundImageLabel;

public class ChatController implements ActionListener {
    private final ChatClientDAO chatClientDAO;
    private  SigninController signin;
    private final ClientGui userView;
    
    private final JList<String> contactList;
    private  JList<String> emailList;
    private final JTextField messageInput;
    private final JTextField searchField;
    private final JPanel messagePanel;
    private final JPanel bottomPanel;
    private final JScrollPane messageScroll;
    private final JLabel imageLabel;
    
    private final String currentUserName;
    private String selectedImagePath;
    private String loggedInUserEmail;
    private String selectedUserEmail;
    
    private File selectedFile;
    private ImageIcon selectedUserIcon;

  

    private final Map<String, List<JLabel>> chatHistory = new HashMap<>();
    private final Box.Filler bottomFiller = new Box.Filler(
        new Dimension(0, 0), new Dimension(0, 0), new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    

    public ChatController(ClientGui userView, String userEmail) throws IOException {
        this.userView = userView;
        this.chatClientDAO = new ChatClientDAO();
        
        this.loggedInUserEmail = userEmail;
        this.contactList = userView.getContactList();
        this.messageInput = userView.getMessageInput();
        this.messagePanel = userView.getMessagePanel();
        this.messageScroll = userView.getMessageScroll();
        this.searchField = userView.getSearchField();
        this.bottomPanel = userView.getBottomPanel();
        this.imageLabel = userView.getImageLabel();
        this.currentUserName = userView.getCurrentUsername();

        userView.getImageLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    handleImageClick();
                } catch (IOException ex) {
                    Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        });  
        
        updateUserImage();
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
            updateContactList(msg.getMessage());
            
        } else if (contactList.getSelectedValue() != null) {
            
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
            
            String Email = chatClientDAO.getEmail(names[0], names.length > 1 ? names[1] : "");
            sendMessage(currentUserName, Email, text);
            
            this.selectedUserEmail = Email;

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
    
    public void handleImageClick() throws IOException {
        
        System.out.println("Image clicked!");
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select an Image");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            
            this.selectedFile = selectedFile;

            // Update image label in GUI
            ImageIcon icon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH));
            BufferedImage img = ImageIO.read(selectedFile);
            ((RoundImageLabel) imageLabel).setImage(img);
            
            Boolean success = chatClientDAO.updateUserImagePath(loggedInUserEmail, path);
            String results = success ? "saved ":"didn't save";
            

            
            System.out.println("image path " + results + " "+ loggedInUserEmail + selectedFile);
                
        }
    }
    
    
    private void updateUserImage() throws IOException {
        String currentUserImagePath = chatClientDAO.getImagePath(loggedInUserEmail);
        String allUserImagePath = chatClientDAO.getImagePath(selectedUserEmail);

        if (currentUserImagePath != null && !currentUserImagePath.isEmpty()) {
            File currentUserFile = new File(currentUserImagePath);
            if (currentUserFile.exists()) {
                BufferedImage img = ImageIO.read(currentUserFile);
                ((RoundImageLabel) imageLabel).setImage(img);
            } else {
                System.err.println("Current user image file not found: " + currentUserImagePath);
            }
        }

        if (allUserImagePath != null && !allUserImagePath.isEmpty()) {
            File friendFile = new File(allUserImagePath);
            if (friendFile.exists()) {
                BufferedImage friendImg = ImageIO.read(friendFile);
                ((RoundImageLabel) imageLabel).setImage(friendImg);
            } else {
                System.err.println("Friend user image file not found: " + allUserImagePath);
            }
        }
    }
    
//    private void showImagePopup(String imagePath) {
//        ImageIcon fullSizeIcon = new ImageIcon(imagePath);
//        JLabel fullImageLabel = new JLabel(fullSizeIcon);
//        JScrollPane scrollPane = new JScrollPane(fullImageLabel);
//        scrollPane.setPreferredSize(new Dimension(400, 400));
//
//        JOptionPane.showMessageDialog(null, scrollPane, "Full Image", JOptionPane.PLAIN_MESSAGE);
//    }
}
