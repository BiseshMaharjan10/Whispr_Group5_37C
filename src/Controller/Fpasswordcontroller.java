
package Controller;

import Dao.UserDAO;

/**
 *
 * @author bibek
 */
public class Fpasswordcontroller {
    private UserDAO userDAO = new UserDAO();

    public String register(String email) {
  
        // Validate email format
        boolean isValid = email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$" );
        
        if (!isValid ||email.equals("Enter your Email address")) {
            return "Enter a valid email";
        }
        
        // Check if email already exists in the database
        else if (!userDAO.isEmailExists(email)) {
            return "Email doesn't exist";
        }
        return null;
    }
}
