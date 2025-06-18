package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Controller.ChatController;
import Dao.ChatClientDAO;
import java.io.IOException;



public class ClientGui extends JFrame {

    private JTextField messageInput;
    private JButton sendButton;
    private JList<String> contactList;
    private JPanel bottompanel;
    private JPanel messagePanel;
    private JScrollPane messageScroll;
    private ChatController controller;

    public ClientGui(String currentUserName) {
        setTitle("Whispr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#FCFBF4"));

        messageInput = new JTextField();
        contactList = new JList<>();
        
        //for bottom part
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);
        messageScroll = new JScrollPane(messagePanel);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(messageScroll, BorderLayout.CENTER);
        
        
        

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
        controller = new ChatController(dao, contactList, messageInput, messagePanel, messageScroll,currentUserName);

        List<String> contactNames = controller.getAllUserFullNames();
        contactList.setListData(contactNames.toArray(new String[0]));
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer(new ContactCellRenderer());

        JScrollPane contactScroll = new JScrollPane(contactList);
        contactScroll.setPreferredSize(new Dimension(150, 0));
        add(contactScroll, BorderLayout.WEST);

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
