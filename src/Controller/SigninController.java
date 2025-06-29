package Controller;

import Dao.UserDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.ClientGui;
import view.Signin;

public class SigninController {
    private final UserDAO userDAO = new UserDAO();
    private final Signin signin;
    private  String currentUserEmail;
    
    public SigninController (Signin signin){
        this.signin = signin;
//        this.signin.getSigninButton().addActionListener(new LoginListener());
    }
    public String loginUser(String email, String password) {
        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!isValid) {
            return "Enter a valid email";
        }
        
        // Check credentials in database
        boolean success = userDAO.Logincredentials(email, password);
        String username = userDAO.Checknames(email);
        this.currentUserEmail = email;
                
        if(success){
            if (username != null && !username.trim().isEmpty()) {
                SwingUtilities.invokeLater(() -> {
               try {
                   // Create GUI
                   ClientGui gui = new ClientGui(username);
                   
                   
                   // Create Controller and wire its
                   ChatController controller = new ChatController(gui,email);
                   
                   // Set contact list (assuming you want to preload users here)
//                   List<String> contactNames = model.getAllUserFullName();
//                   gui.setContactListData(contactNames); // use a setter method inside ClientGui
//                   
                   // Connect Listeners
                   gui.addSendButtonListener(controller.getSendActionListener());
                   gui.addMessageInputListener(controller.getSendActionListener());
                   gui.addContactListSelectionListener(e -> {
                       String selected = gui.getSelectedContact();
                       if (selected != null) {
                           controller.showMessages(selected);
                           gui.getBottomPanel().setVisible(true);
                       }
                   });
                   gui.addSearchButtonListener(e -> gui.toggleSearchPanel(true));
                   gui.addSearchFieldListener(new javax.swing.event.DocumentListener() {
                       @Override
                       public void insertUpdate(javax.swing.event.DocumentEvent e) {
                           controller.highlightMessages();
                       }
                       
                       @Override
                       public void removeUpdate(javax.swing.event.DocumentEvent e) {
                           controller.highlightMessages();
                       }
                       
                       @Override
                       public void changedUpdate(javax.swing.event.DocumentEvent e) {
                           controller.highlightMessages();
                       }
                   });
                   
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // or log it properly
                   
                   // Show GUI
                   gui.setVisible(true);
               } catch (IOException ex) {
                        Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);  
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                JOptionPane.showMessageDialog(null, "Username is required.");
                System.exit(0);
            }
        }
        return null;
    }
    
    void open() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}





