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
    private JButton profileButton; // 👤 Profile button

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
            if (currentlyVisible) {
                searchField.setText("");
                controller.highlightMessages();
            }
        });

        // 👤 Profile Button
        profileButton = new JButton("👤 Profile");
        profileButton.setFont(new Font("Arial", Font.PLAIN, 14));
        profileButton.addActionListener(e -> {
            Profile profilePage = new Profile();
            profilePage.updateName(currentUserName); // Or hardcode like "Suraj Maharjan"
            profilePage.updateProfilePic("src/view/NOpfp.jpg"); // Adjust to actual image path
            profilePage.setVisible(true);
        });

        messageInput = new JTextField();
        contactList = new JList<>();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(profileButton);
        topPanel.add(searchPanel);
        topPanel.add(searchButton);

        // 💬 Message area
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);
        messageScroll = new JScrollPane(messagePanel);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel messageArea = new JPanel(new BorderLayout());
        messageArea.add(topPanel, BorderLayout.NORTH);
        messageArea.add(messageScroll, BorderLayout.CENTER);

        // 🔎 Real-time search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { controller.highlightMessages(); }
            public void removeUpdate(DocumentEvent e) { controller.highlightMessages(); }
            public void changedUpdate(DocumentEvent e) { controller.highlightMessages(); }
        });

        // ⌨️ Input bar and status
        bottompanel = new JPanel(new BorderLayout());
        bottompanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

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
        bottompanel.setPreferredSize(new Dimension(0, 80));
        bottompanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // 🧑‍🤝‍🧑 Contacts
        ChatClientDAO dao = new ChatClientDAO();
        controller = new ChatController(dao, contactList, messageInput, messagePanel, messageScroll, currentUserName, searchField, bottompanel);

        List<String> contactNames = controller.getAllUserFullNames();
        contactList.setListData(contactNames.toArray(new String[0]));
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer(new ContactCellRenderer());

        JScrollPane contactScroll = new JScrollPane(contactList);
        contactScroll.setPreferredSize(new Dimension(150, 0));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contactScroll, messageArea);
        splitPane.setDividerLocation(150);
        splitPane.setDividerSize(1);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);
        add(bottompanel, BorderLayout.SOUTH);

        InputMap im = contactList.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("DOWN"), "none");
        im.put(KeyStroke.getKeyStroke("UP"), "none");

        sendButton.addActionListener(controller);
        messageInput.addActionListener(controller);

        contactList.addListSelectionListener(e -> {
            String selected = contactList.getSelectedValue();
            if (selected != null) {
                controller.showMessages(selected, bottompanel);
                bottompanel.setVisible(true);
            }
        });
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
