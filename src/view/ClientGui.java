package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalTime;
import Controller.ChatController;
import Dao.ChatClient;


public class ClientGui extends JFrame {

    private Map<String, java.util.List<JLabel>> chatHistory = new HashMap<>();
    private JTextField messageInput;
    private JButton sendButton;
    private JList<String> contactList;
    private JPanel bottompanel;
    private JPanel messagePanel;
    private JScrollPane messageScroll;

    public ClientGui() {
        setTitle("Whispr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#FCFBF4"));


        // Controller will provide full names from the DAO
        ChatController controller = new ChatController(new ChatClient());
        java.util.List<String> contactNames = controller.getAllUserFullNames();
        contactList = new JList<>(contactNames.toArray(new String[0]));

        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setCellRenderer(new ContactCellRenderer());
        // Disable arrow key navigation
        InputMap im = contactList.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("DOWN"), "none");
        im.put(KeyStroke.getKeyStroke("UP"), "none");
        JScrollPane contactScroll = new JScrollPane(contactList);
        contactScroll.setPreferredSize(new Dimension(150, 0));
        add(contactScroll, BorderLayout.WEST);

        
        // Message Panel (Center)
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        messageScroll = new JScrollPane(messagePanel);
        messageScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(messageScroll, BorderLayout.CENTER);

        // Bottom Panel (Input + Send)
        bottompanel = new JPanel(new BorderLayout());
        messageInput = new JTextField();
        messageInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                sendCurrentMessage();
            }
        });
        messageInput.setFont(new Font("Arial",Font.PLAIN, 18));
        messageInput.setPreferredSize(new Dimension(0, 40));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.PLAIN, 18));
        sendButton.setPreferredSize(new Dimension(100, 40));
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCurrentMessage();
            }
        });
        bottompanel.add(messageInput, BorderLayout.CENTER);
        bottompanel.add(sendButton, BorderLayout.EAST);
        bottompanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        bottompanel.setVisible(false); // Initially hidden

        messagePanel.add(Box.createVerticalGlue()); // Push messages up
        messagePanel.add(bottompanel);

        // Handle contact selection
        contactList.addListSelectionListener(e -> {
            String selectedContact = contactList.getSelectedValue();
            if (selectedContact != null) {
                showMessages(selectedContact);
                bottompanel.setVisible(true);
            }
        });

        
    }
    
    


    private void sendCurrentMessage() {
        String selectedContact = contactList.getSelectedValue();
        String text = messageInput.getText().trim();
    
        if (!text.isEmpty() && selectedContact != null) {
            chatHistory.putIfAbsent(selectedContact, new java.util.ArrayList<>());
    
            String timestamp = LocalTime.now().withSecond(0).withNano(0).toString();  // HH:MM
            JLabel messageLabel = new JLabel("<html><div style='padding: 8px; background: #DCF8C6; border-radius: 10px; max-width: 300px; text-align: right;'>" + text + "<br><span style='font-size: 10px; color: gray;'>" + timestamp + "</span></div></html>");
            messageLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a contact and enter a message.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    class ContactCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
    
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
    
            label.setHorizontalAlignment(SwingConstants.CENTER); // Center text
            label.setFont(new Font("Arial", Font.PLAIN, 18));     // Larger font
            label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Spacing
    
            return label;
        }
    }


    private void showMessages(String contact) {
        messagePanel.removeAll();
    
        java.util.List<JLabel> messages = chatHistory.getOrDefault(contact, new java.util.ArrayList<>());
        for (JLabel label : messages) {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            wrapper.setOpaque(false);
            wrapper.add(label);
            messagePanel.add(wrapper);
            messagePanel.add(Box.createVerticalStrut(5)); // spacing between messages
        }
    
        messagePanel.add(Box.createVerticalGlue());     // 🟢 This pushes the input area to the bottom
        messagePanel.add(bottompanel);                  // 🟢 Input area at the bottom
    
        messagePanel.revalidate();
        messagePanel.repaint();
    
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = messageScroll.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }}

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MessagingDashboard().setVisible(true));
//    }
//}
