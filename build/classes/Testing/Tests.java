import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tests {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Tests::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Group Chat - Whispr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        JTextField messageField = new JTextField();
        JButton sendButton = new JButton("Send");

        // Panel for input
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Layout setup
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Event: Send Message
        ActionListener sendAction = e -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append("You: " + message + "\n");
                messageField.setText("");
            }
        };

        sendButton.addActionListener(sendAction);
        messageField.addActionListener(sendAction); // Press Enter to send

        frame.setVisible(true);
    }
}