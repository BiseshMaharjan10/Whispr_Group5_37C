package utils;
import view.ClientGui;

import javax.swing.*;

public class Main {
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        ClientGui gui = new ClientGui();
        gui.setVisible(true);
    });
}
}



