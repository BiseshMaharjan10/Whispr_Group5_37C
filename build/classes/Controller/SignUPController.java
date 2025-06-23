package Controller;

import Dao.UserDAO;
import Model.UserModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.Signup;
import view.Signin;

public class SignUPController {
    private final UserDAO userDAO = new UserDAO();
    private final Signup userview;
    
    public SignUPController(Signup userview){
        this.userview=userview;
        userview.addAddUserListener(new AddUserListener());
        userview.addLoginListener(new LoginListener());
    }
    public void open(){
        this.userview.setVisible(true);
    }
    public void close(){
        this.userview.dispose();
    }
  class AddUserListener implements ActionListener {
      @Override
       public void actionPerformed(ActionEvent e) {
            try{
                String firstname=userview.getFirstName().getText();
                String lastname=userview.getLastName().getText();
                String email=userview.getEmailField().getText();
                String password=userview.getPasswordField().getText();
                String confirmpassword=userview.getConfrimPassword().getText();

         UserModel user = new UserModel (firstname,lastname,email,password,confirmpassword) ;
 
        boolean check = userDAO.CheckUser(user.getEmail());
        if(check) { 
            JOptionPane.showMessageDialog(null, "Email already in use!");
        }
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!isValid) {
             JOptionPane.showMessageDialog(null, "Enter a valid email");
        }else if (password.equals("Set Password")) {
             JOptionPane.showMessageDialog(null, "Please enter a password");
        } else if (!password.equals(confirmpassword)) {
           JOptionPane.showMessageDialog(null, "Confirm password didn't match!");
        }else if (firstname.equals("First Name") || lastname.equals("Last Name")){
            JOptionPane.showMessageDialog(null,  "Please fill all the fields");
        }else{
             userDAO.registerUser(user); 
             JOptionPane.showMessageDialog(null,"User registered successfully!");   
             userview.dispose();
        }
            }catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"An error occured: "+ex.getMessage()); 
            }
        }
    }
  class LoginListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           Signin loginview = new Signin();
           SigninController login = new SigninController(loginview);
           login.open();
           close();
        }
      
  }
}