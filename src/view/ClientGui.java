package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Controller.ChatController;
import Dao.ChatClientDAO;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



public class ClientGui extends JFrame {

    private JTextField messageInput;
    private JButton sendButton;
    private JList<String> contactList;
    private JPanel bottompanel;
    private JPanel messagePanel;
    private JScrollPane messageScroll;
    private ChatController controller;
    private JTextField searchField;
    private JPanel searchPanel;
    


    public ClientGui(String currentUserName) {
        setTitle("Whispr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#FCFBF4"));
        
        
        // 🔍 Top Panel with Search
        searchField = new JTextField(20);
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setVisible(false);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("🔍");
        searchButton.addActionListener(e -> {
        boolean currentlyVisible = searchPanel.isVisible();
        searchPanel.setVisible(!currentlyVisible);

        // Optional: clear search field and reset messages when hiding
        if (currentlyVisible) {
            searchField.setText("");
            controller.highlightMessages();
            }
        });
        

        messageInput = new JTextField();
        contactList = new JList<>();
        
        JPanel topPanel = new JPanel(new BorderLayout());
        // Create the profile picture label to show the uploaded image

        //  Profile Button
        JButton profileButton = new JButton("👤");
        profileButton.setFocusPainted(false);
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        profileButton.setFont(new Font("Arial", Font.PLAIN, 18));
        profileButton.setToolTipText("Upload Profile Picture ");
        

        topPanel.add(searchButton, BorderLayout.WEST);      // Search icon on the left
        topPanel.add(searchPanel, BorderLayout.CENTER);     // Search field in center
        topPanel.add(profileButton, BorderLayout.EAST);     // Profile icon on the right
        
        profileButton.addActionListener(e -> {
            Profile profile = new Profile();
            System.out.println("Clicked");
            profile.setVisible(true);
        });
        

        
        //for bottom part
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);
        messageScroll = new JScrollPane(messagePanel);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel messageArea = new JPanel(new BorderLayout());
        messageArea.add(topPanel, BorderLayout.NORTH);
        messageArea.add(messageScroll, BorderLayout.CENTER);

        
         //  Real-time search listener
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                controller.highlightMessages();
            }

            public void removeUpdate(DocumentEvent e) {
                controller.highlightMessages();
            }

            public void changedUpdate(DocumentEvent e) {
                controller.highlightMessages();
            }
        });

        
        
        

        // Unified bottom panel with input, send button, and "Logged in as"
        bottompanel = new JPanel(new BorderLayout());
        bottompanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//        bottompanel.setPreferredSize(new Dimension(0, 60));

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

        bottompanel.add(inputPanel, BorderLayout.CENTER);
        bottompanel.add(currentUserLabel, BorderLayout.SOUTH);
        bottompanel.setVisible(false);



        // Contacts
        ChatClientDAO dao = new ChatClientDAO();
        controller = new ChatController(dao, contactList, messageInput, messagePanel, messageScroll,currentUserName, searchField, bottompanel);

        List<String> contactNames = controller.getAllUserFullNames();
        contactList.setListData(contactNames.toArray(new String[0]));
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer(new ContactCellRenderer());

        JScrollPane contactScroll = new JScrollPane(contactList);
        contactScroll.setPreferredSize(new Dimension(150, 0));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contactScroll, messageArea);
        splitPane.setDividerLocation(150); // Width of left panel
        splitPane.setDividerSize(1);       // Thin dividing line
        splitPane.setEnabled(false);       // Make it non-draggable
        add(splitPane, BorderLayout.CENTER);
        // Disable arrow navigation
        InputMap im = contactList.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("DOWN"), "none");
        im.put(KeyStroke.getKeyStroke("UP"), "none");

        // Action Listener setup
        sendButton.addActionListener(controller);
        messageInput.addActionListener(controller);

        contactList.addListSelectionListener(e -> {
            String selected = contactList.getSelectedValue();
            if (selected != null) {
                controller.showMessages(selected, bottompanel);
                bottompanel.setVisible(true);
            }
        });

            bottompanel.setPreferredSize(new Dimension(0, 80)); // fixed height
            bottompanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // ensure it never grows taller
            bottompanel.setPreferredSize(new Dimension(0, 80)); // fixed height
            bottompanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // ensure it never grows taller

            add(bottompanel, BorderLayout.SOUTH);
    }

    class ContactCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            return label;
        }
    }
}