package Controller;

import Dao.UserDAO;
import Model.UserModel;

public class SignUPController {
    private UserDAO userDAO = new UserDAO();

    public String registerUser(String firstname,String lastname,String email,String password, String confirmPassword) {
 
        boolean isverified = false;

        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!isValid) {
            return "Enter a valid email";
        }

        // Check for empty/default password
        else if (password.equals("Set Password")) {
            return "Please enter a password";
        }
        
        else if (!password.equals(confirmPassword)) {
             return "Confirm password didn't match!";
        }
        
        else if (firstname.equals("First Name") || lastname.equals("Last Name")){
            return "Please fill all the fields";
        }
 

        // Check if email already exists in the database
        else if (userDAO.isEmailExists(email)) {
            return "Email already in use!";
        }


        // Insert user into the database using model
        else {
            UserModel user = new UserModel(firstname, lastname, email, password, isverified);
            boolean success = userDAO.registerUser(user);
            


            return success ? "User registered successfully!" : "Registration failed!";
        }
    }
}