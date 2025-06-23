package Controller;

import Dao.UserDAO;
import javax.swing.SwingUtilities;
import view.ClientGui;
import view.Signin;

public class SigninController {
    private final UserDAO userDAO = new UserDAO();
    private final Signin signin;
    
    public SigninController (Signin signin){
        this.signin = signin;
        
    }
    public String loginUser(String email, String password) {
        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!isValid) {
            return "Enter a valid email";
        }
        
        // Check credentials in database
        boolean success = userDAO.Logincredentials(email, password);
        String full_name = userDAO.Checknames(email);

        
        if(success){ 
            SwingUtilities.invokeLater(() -> new ClientGui(full_name).setVisible(true));
            signin.dispose();
        }else {
            return "Credentials didn't match";
        }
        return null;
    }

    void open() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}




