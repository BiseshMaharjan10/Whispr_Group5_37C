package Controller;


import Dao.UserDAO;

public class SigninController {
    private UserDAO userDAO = new UserDAO();

    public String loginUser(String email, String password) {
        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        if (!isValid) {
            return "Enter a valid email";
        }
        
        // Check credentials in database
        boolean success = userDAO.Logincredentials(email, password);
        return success ? "You are logged in" : "Credentials didn't match";
    }
}
