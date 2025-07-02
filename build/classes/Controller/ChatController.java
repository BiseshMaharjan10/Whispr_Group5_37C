package Controller;

import Controller.SigninController;
import Dao.ChatClientDAO;
import Model.MessageModel;
import view.ClientGui;
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
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.Profile;
import view.RoundImageLabel;



public class ChatController implements ActionListener {
    private  ChatClientDAO chatClientDAO;
    private  SigninController signin;
    private  ClientGui userView;
    private MessageModel model;
    
    private  JList<String> contactList;
    private  JList<String> emailList;
    private  JTextField messageInput;
    private  JTextField searchField;
    private  JPanel messagePanel;
    private  JPanel bottomPanel;
    private  JScrollPane messageScroll;
    private  JLabel imageLabel;
    
    private  String currentUserName;
    private String selectedImagePath;
    private String loggedInUserEmail;
    private String selectedUserEmail;
    public String selectedUserName;
    
    private File selectedFile;
    private ImageIcon selectedUserIcon;

  

    private final Map<String, List<JLabel>> chatHistory = new HashMap<>();
    private final Box.Filler bottomFiller = new Box.Filler(
        new Dimension(0, 0), new Dimension(0, 0), new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    

    public ChatController(String selectedUserName) {
        
        this.selectedUserName = selectedUserName;
    }

    public ChatController(ClientGui userView, String userEmail) throws IOException {
        this.userView = userView;
        this.chatClientDAO = new ChatClientDAO();
        this.model = new MessageModel();
        
        this.loggedInUserEmail = userEmail;
        this.contactList = userView.getContactList();
        this.messageInput = userView.getMessageInput();
        this.messagePanel = userView.getMessagePanel();
        this.messageScroll = userView.getMessageScroll();
        this.searchField = userView.getSearchField();
        this.bottomPanel = userView.getBottomPanel();
        this.imageLabel = userView.getImageLabel();
        this.currentUserName = userView.getCurrentUsername();
        
        
        MessageModel model = new MessageModel(selectedUserName);

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
        
        
        userView.addProfileListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Profile profileView = new Profile();
            new ProfileController(profileView, ChatController.this);
            profileView.setVisible(true);
            profileView.updateName(selectedUserName);
            
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

    public void updateContactList(String csv) {
        SwingUtilities.invokeLater(() -> {
            // If contactList model is not yet set, initialize it with users from DB
            if (contactList.getModel() == null || !(contactList.getModel() instanceof DefaultListModel)) {               
                DefaultListModel<String> temp_model = new DefaultListModel<>();
                for (String name : getAllUserFullNames()) {
                    if (!name.equals(currentUserName)) {
                        temp_model.addElement(name);
                    }
                }
                contactList.setModel(temp_model);
            }

            DefaultListModel<String> temp_model = (DefaultListModel<String>) contactList.getModel();

            // Now add new users from csv
            for (String user : csv.split(",")) {
                user = user.trim();
                if (!user.equals(currentUserName) && !temp_model.contains(user) && !user.equals("")) {
                    temp_model.addElement(user); // Add only if not already in list
                }
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
            String tick = isOwnMessage ? " <span style='color:#34B7F1;' class='tick'>&#10003;</span>" : ""; // single tick for sent messages
            JLabel msgLabel = new JLabel("<html><div style='padding: 8px; background: " + bubbleColor +
                "; border-radius: 10px; max-width: 300px; text-align: " + align + ";'>" +
                message + "<br><span style='font-size: 10px; color: gray;'>" + timestamp + tick + "</span></div></html>");

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
    
    public Map<String, String> getUserImageMap() {
        Map<String, String> userImageMap = new HashMap<>();
        ChatClientDAO dao = new ChatClientDAO();
        List<MessageModel> allUsers = dao.getAllUsers(); // assuming it returns user first/last/email

        for (MessageModel user : allUsers) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            String email = dao.getEmail(user.getFirstName(), user.getLastName()); // or use user.getEmail() directly
            String imagePath = dao.getImagePath(email);

            if (imagePath != null) {
                userImageMap.put(fullName, imagePath);
            } else {
                userImageMap.put(fullName, "/images/default.png"); // fallback image
            }
        }
        return userImageMap;
    }
}
