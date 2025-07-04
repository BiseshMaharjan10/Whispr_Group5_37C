//package Controller;
//
//import Controller.SignUPController.AddUserListener.LoginListener;
//import Dao.UserDAO;
//import Model.UserModel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.JOptionPane;
//import view.Signup;
//import view.Signin;
//
//public class SignUPController {
//    private final UserDAO userDAO = new UserDAO();
//    private final Signup userview;
//    
//    public SignUPController(Signup userview){
//        this.userview=userview;
//        userview.addAddUserListener(new AddUserListener());
//        userview.addLoginListener(new LoginListener());
//    }
//    public void open(){
//        this.userview.setVisible(true);
//    }
//    public void close(){
//        this.userview.dispose();
//    }
//  class AddUserListener implements ActionListener {
//      @Override
//       public void actionPerformed(ActionEvent e) {
//            try{
//                String firstname=userview.getFirstName().getText();
//                String lastname=userview.getLastName().getText();
//                String email=userview.getEmailField().getText();
//                String password=userview.getPasswordField().getText();
//                String confirmpassword=userview.getConfrimPassword().getText();
//                
//                if (firstname == null || lastname == null || email == null || password == null || confirmpassword == null) {
//                    // At least one is null
//                
//                    UserModel user = new UserModel (firstname,lastname,email,password,confirmpassword) ;
//
//
//                   boolean check = userDAO.CheckUser(user.getEmail());
//                   if(check) { 
//                       JOptionPane.showMessageDialog(null, "Email already in use!");
//                   }
//                   boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
//                   if (!isValid) {
//                        JOptionPane.showMessageDialog(null, "Enter a valid email");
//                   }else if (password.equals("Set Password")) {
//                        JOptionPane.showMessageDialog(null, "Please enter a password");
//                   } else if (!password.equals(confirmpassword)) {
//                      JOptionPane.showMessageDialog(null, "Confirm password didn't match!");
//                   }else if (firstname.equals("First Name") || lastname.equals("Last Name")){
//                       JOptionPane.showMessageDialog(null,  "Please fill all the fields");
//                   }else{
//                        userDAO.registerUser(user); 
//                        JOptionPane.showMessageDialog(null,"User registered successfully!");   
//                        userview.dispose();
//                    }
//                }
//            }catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(null,"An error occured: "+ex.getMessage()); 
//            }
//                
//          
//        
//       
//    }
//  class LoginListener implements ActionListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//           Signin loginview = new Signin();
//           SigninController login = new SigninController(loginview);
//           login.open();
//           close();
//        }
//      
//  }
//}







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

    public SignUPController(Signup userview) {
        this.userview = userview;
        userview.addAddUserListener(new AddUserListener());
        userview.addLoginListener(new LoginListener());
    }

    public void open() {
        this.userview.setVisible(true);
    }

    public void close() {
        this.userview.dispose();
    }

    class AddUserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String firstname = userview.getFirstName().getText().trim();
                String lastname = userview.getLastName().getText().trim();
                String email = userview.getEmailField().getText().trim();
                String password = userview.getPasswordField().getText();
                String confirmpassword = userview.getConfrimPassword().getText();

                // Field empty check
                if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()
                        || password.isEmpty() || confirmpassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }

                // Email validation
                boolean isValidEmail = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
                if (!isValidEmail) {
                    JOptionPane.showMessageDialog(null, "Enter a valid email.");
                    return;
                }

                // Duplicate email check
                if (userDAO.CheckUser(email)) {
                    JOptionPane.showMessageDialog(null, "Email already in use!");
                    return;
                }

                // Password checks
                if (password.equals("Set Password")) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid password.");
                    return;
                }

                if (!password.equals(confirmpassword)) {
                    JOptionPane.showMessageDialog(null, "Confirm password didn't match!");
                    return;
                }

                // All good — create user and register
                UserModel user = new UserModel(firstname, lastname, email, password, confirmpassword);
                userDAO.registerUser(user);
                JOptionPane.showMessageDialog(null, "User registered successfully!");
                userview.dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
            }
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Signin loginview = new Signin();
            SigninController login = new SigninController(loginview);
            login.open();
            close();
        }
    }
}
