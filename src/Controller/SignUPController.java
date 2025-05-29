package Controller;

import Dao.UserDAO;
import Model.UserModel;

public class SignUPController {
    private UserDAO userDAO = new UserDAO();

    public String register(String f_name, String l_name, String email, String password, String confirmPassword) {
        Boolean isverified = false;

        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!isValid) {
            return "Enter a valid email";
        }

        // Check for empty/default password
        else if (password.equals("Set Password")) {
            return "Please enter a password";
        }

        // Check if passwords match
        else if (!password.equals(confirmPassword)) {
            return "Confirm password didn't match!";
        }

        // Check if email already exists in the database
        else if (userDAO.isEmailExists(email)) {
            return "Email already in use!";
        }

        // Insert user into the database using model
        else {
            UserModel user = new UserModel(f_name, l_name, email, password, isverified);
            boolean success = userDAO.registerUser(user);
            return success ? "User registered successfully!" : "Registration failed!";
        }
    }
}