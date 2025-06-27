package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
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
    private JPanel bottomPanel;
    private JPanel messagePanel;
    private JScrollPane messageScroll;
    private JTextField searchField;
    private JPanel searchPanel;
    private JLabel imageLabel;
    private JButton searchButton;
    private JLabel timerLabel;
    private JLabel dynamicTextLabel;
    private String currentUserName;


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

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(searchPanel);
        topPanel.add(searchButton);

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

        JLabel currentUserLabel = new JLabel("Logged in as: " + currentUserName);
        currentUserLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        currentUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(currentUserLabel, BorderLayout.SOUTH);

        JPanel messageArea = new JPanel(new BorderLayout());
        messageArea.add(topPanel, BorderLayout.NORTH);
        messageArea.add(messageScroll, BorderLayout.CENTER);
        messageArea.add(bottomPanel, BorderLayout.SOUTH);

        JScrollPane contactScroll = new JScrollPane(contactList);
        contactScroll.setPreferredSize(new Dimension(150, 0));
        contactScroll.setBorder(BorderFactory.createTitledBorder("Your friends"));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(contactScroll, BorderLayout.CENTER);

        JLabel logoutLabel = new JLabel("Log out");
        logoutLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        logoutLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoutLabel.setBorder(BorderFactory.createEmptyBorder(15, 8, 5, 0));
        leftPanel.add(logoutLabel, BorderLayout.SOUTH);

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(50, 50));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 17)); // top, left, bottom, right

        dynamicTextLabel = new JLabel(currentUserName);
        dynamicTextLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dynamicTextLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dynamicTextLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel bottomInfoPanel = new JPanel(new GridLayout(1, 2));
        bottomInfoPanel.add(imageLabel);
        bottomInfoPanel.add(dynamicTextLabel);
        leftPanel.add(bottomInfoPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, messageArea);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(1);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);

        InputMap im = contactList.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("DOWN"), "none");
        im.put(KeyStroke.getKeyStroke("UP"), "none");

        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer(new ContactCellRenderer());
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

    public void toggleSearchPanel(boolean visible) {
        searchPanel.setVisible(visible);
    }

    public void clearSearchField() {
        searchField.setText("");
    }

    //setters
    public void setContactListData(List<String> contacts) {
        contactList.setListData(contacts.toArray(new String[0]));
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

    public void clearMessageInput() {
        messageInput.setText("");
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
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            return label;
        }
    }
}
