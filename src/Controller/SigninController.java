package Controller;

import Dao.UserDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import utils.Main;
import view.Signin;

public class SigninController {
    private final UserDAO userDAO = new UserDAO();
    private final Signin signin;
    
    public SigninController (Signin signin){
        this.signin = signin;
        this.signin.getSigninButton().addActionListener(new LoginListener());
    }
    
    public void open() {
        this.signin.setVisible(true);
    }
    public void close() {
        this.signin.dispose();
    }
    
    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = signin.getEmailField().getText().trim();
            String password = String.valueOf(signin.getPasswordField().getPassword());
            // Validate email format
            boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
            if (!isValid) {
                JOptionPane.showMessageDialog(signin, "Enter a valid email");
                return;
            }
            // Check credentials in database
            boolean success = userDAO.Logincredentials(email, password);
            String full_name = userDAO.Checknames(email);
            if(success){ 
                Main.main(new String[0]);
                signin.dispose();
            } else {
                JOptionPane.showMessageDialog(signin, "Credentials didn't match");
            }
        }
    }
}




