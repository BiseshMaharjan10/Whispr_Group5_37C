package view;

import Controller.ChatController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

public class ClientGui extends JFrame {

    private JTextField messageInput;
    private JButton sendButton;
    private JList<String> contactList;
    private JList<String> emailList;
    private JPanel bottomPanel;
    private JPanel messagePanel;
    private JScrollPane messageScroll;
    private JTextField searchField;
    
    private JLabel onlineStatus;
    
    private JPanel searchPanel;
    private JButton profileButton;
    private JLabel imageLabel;
    private JButton searchButton;
    private JLabel timerLabel;
    private JLabel dynamicTextLabel;
    private String currentUserName;
    private ChatController controller;
    private  JLabel friendsImageLabel;


    public ClientGui(String currentUserName) {
        
        setTitle("Whispr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#FCFBF4"));

        
        initComponents(currentUserName);
    }

    private void initComponents(String currentUserName) {
        
        this.currentUserName = currentUserName;
        
        searchField = new JTextField(20);
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setVisible(false);
        searchPanel.add(searchField);

        searchButton = new JButton("\uD83D\uDD0D");

        messageInput = new JTextField();
        contactList = new JList<>();
        
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setOpaque(true);
        
        profileButton = new JButton("👤");
        profileButton.setFocusPainted(false);
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        profileButton.setFont(new Font("Arial", Font.PLAIN, 18));
        profileButton.setToolTipText("Upload Profile Picture ");
        profileButton.setVisible(false); 
        
        onlineStatus = new JLabel(" 🟢 Online");
        onlineStatus.setVisible(false);
        
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        onlineStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add some spacing between button and label if you want
        onlineStatus.setBorder(BorderFactory.createEmptyBorder(1, 5, 0, 0)); // top, left, bottom, right
        
        profilePanel.add(profileButton);
        profilePanel.add(onlineStatus);
        
        

        

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(profilePanel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        messageScroll = new JScrollPane(messagePanel);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 80));
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        bottomPanel.setVisible(false);

        messageInput.setFont(new Font("Arial", Font.PLAIN, 18));
        messageInput.setPreferredSize(new Dimension(0, 40));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.PLAIN, 18));
        sendButton.setPreferredSize(new Dimension(100, 40));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageInput, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        
        bottomPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel messageArea = new JPanel(new BorderLayout());
        messageArea.add(topPanel, BorderLayout.NORTH);
        messageArea.add(messageScroll, BorderLayout.CENTER);
        messageArea.add(bottomPanel, BorderLayout.SOUTH);

        JScrollPane contactScroll = new JScrollPane(contactList);
        contactScroll.setPreferredSize(new Dimension(220, 0)); //width, height
        contactScroll.setBorder(BorderFactory.createTitledBorder("Your friends"));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(contactScroll, BorderLayout.CENTER);

        imageLabel = new RoundImageLabel(null);
        imageLabel.setPreferredSize(new Dimension(50, 50));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // top, left, bottom, right

        dynamicTextLabel = new JLabel(currentUserName);
        dynamicTextLabel.setFont(new Font("Arial", Font.BOLD, 12));
        dynamicTextLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dynamicTextLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel bottomInfoPanel = new JPanel(new GridLayout(1, 2));
        bottomInfoPanel.add(imageLabel);
        bottomInfoPanel.add(dynamicTextLabel);
        leftPanel.add(bottomInfoPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, messageArea);
        splitPane.setDividerLocation(220);
        splitPane.setDividerSize(1);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);

        InputMap im = contactList.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("DOWN"), "none");
        im.put(KeyStroke.getKeyStroke("UP"), "none");

        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer(new ContactCellRenderer());
        contactList.setFixedCellHeight(60);  // Adjust number to increase vertical spacing
    }

    
    //action listeners
    public void addSendButtonListener(java.awt.event.ActionListener listener) {
        sendButton.addActionListener(listener);
    }
    
    public void addMessageInputListener(java.awt.event.ActionListener listener) {
        messageInput.addActionListener(listener);
    }
    
    public void addContactListSelectionListener(ListSelectionListener listener) {
        contactList.addListSelectionListener(listener);
    }

    public void addSearchFieldListener(DocumentListener listener) {
        searchField.getDocument().addDocumentListener(listener);
    }

    public void addSearchButtonListener(java.awt.event.ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    
    public void addProfileListener(ActionListener listener) {
        profileButton.addActionListener(listener);
    }
    
    
    //setters
    public void toggleSearchPanel(boolean visible) {
        searchPanel.setVisible(visible);
    }

    public void clearSearchField() {
        searchField.setText("");
    }

    public void setContactListData(List<String> contacts) {
        contactList.setListData(contacts.toArray(new String[0]));
    }
    
    public void showProfileButton() {
        profileButton.setVisible(true);
    }
    
    public void showOnlineStatus() {
        onlineStatus.setVisible(true);
    }
    
    public void hideOnlineStatus(){
        onlineStatus.setVisible(false);
    }
    
    public void clearMessageInput() {
        messageInput.setText("");
    }
    

    
    //getters
    
    public String getCurrentUsername(){
        return currentUserName;
    }
    public JTextField getMessageInput() {
        return messageInput;
    }
    
    public JTextField getSearchField() {
        return searchField;
    }
    
    public JList<String> getContactList() {
        return contactList;
    }

    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    public String getMessageText() {
        return messageInput.getText().trim();
    }

    public String getSelectedContact() {
        return contactList.getSelectedValue();
    }

    public JPanel getMessagePanel() {
        return messagePanel;
    }

    public JScrollPane getMessageScroll() {
        return messageScroll;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }
    
    class ContactCellRenderer extends DefaultListCellRenderer {

        private  Map<String, String> userImageMap;

        public ContactCellRenderer(Map<String, String> userImageMap) {
            this.userImageMap = userImageMap;
        }
        public ContactCellRenderer(){
            
        }

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            String userName = value.toString();
            String imagePath = userImageMap.getOrDefault(userName, "/Users/bibek/Documents/screensaver/SCR-20240929-pdyl.jpeg");

            try {
                BufferedImage original = ImageIO.read(new File(imagePath));

                // Crop square from center
                int size = Math.min(original.getWidth(), original.getHeight());
                int x = (original.getWidth() - size) / 2;
                int y = (original.getHeight() - size) / 2;
                BufferedImage cropped = original.getSubimage(x, y, size, size);

                // Resize first with high-quality scaling
                BufferedImage resized = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = resized.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(cropped, 0, 0, 30, 30, null);
                g2.dispose();

                // Create a circular clipped image with smooth edges
                BufferedImage circleBuffer = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = circleBuffer.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Make clip a circle
                g2d.setClip(new Ellipse2D.Float(0, 0, 30, 30));

                // Draw the resized image clipped in circle
                g2d.drawImage(resized, 0, 0, null);
                g2d.dispose();

                label.setIcon(new ImageIcon(circleBuffer));
            } catch (Exception e) {
                e.printStackTrace();
                label.setIcon(new ImageIcon("/Users/bibek/Documents/screensaver/SCR-20240929-pdyl.jpeg"));
            }
            
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            label.setIconTextGap(10);
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            
            Border matte = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY);
            Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10); //top, left, bottom, right
            label.setBorder(BorderFactory.createCompoundBorder(matte, padding));

            return label;
        }
    }
    
    public void setContactListRenderer(Map<String, String> userImageMap) {
        contactList.setCellRenderer(new ContactCellRenderer(userImageMap));
    }
}

