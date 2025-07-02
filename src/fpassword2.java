


public class Fpasswordcontroller {

    public String resetPassword(String password, String confirmPassword) {
        if (password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            return "Password fields cannot be empty";
        }

        if (!password.equals(confirmPassword)) {
            return "Passwords do not match";
        }

        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }

        // Here you would write code to update the password in your database.
        // For now, we simulate success:
        return "Reset password successful";
    }
}
