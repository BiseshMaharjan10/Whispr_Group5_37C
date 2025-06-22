package utils;
import view.ClientGui;
import Controller.SigninController;

import javax.swing.*;

public class Main {
public static void main(String[] args) {
     String username = JOptionPane.showInputDialog("Enter your username:");
     if (username != null && !username.trim().isEmpty()) {
        SwingUtilities.invokeLater(() -> new ClientGui(username).setVisible(true));
     }
     else{
        JOptionPane.showMessageDialog(null, "Username is required.");
        System.exit(0);
     }
    
}
    }



