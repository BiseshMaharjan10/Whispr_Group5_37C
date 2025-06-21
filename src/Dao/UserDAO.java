
 package Dao;


import Database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.logging.Level;
 import java.util.logging.Logger;
import Model.UserModel;
import Model.SigninModel;



public class UserDAO {
    private final MySqlConnection db = new MySqlConnection();
    

    public boolean isEmailExists(String email, String password) {
        Connection conn = db.openConnection();
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
          try {   
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // email found
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            db.closeConnection(conn);
        }
    }


//for registring new users

    public boolean registerUser(UserModel user) {
        Connection conn = db.openConnection();
        try {
            String sql = "INSERT INTO users (first_name, last_name, password,confirmpassword ,is_verified, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getConfirmpPassword());
            ps.setBoolean(5, user.isVerified());
            ps.setString(6, user.getEmail());

            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Registration error: " + e);
            return false;
        } finally {
            db.closeConnection(conn);
        }
    }

    
    
    
    //for updating password
    public boolean UpdatePassword(String email, String newPassword) {
        Connection conn = db.openConnection();
        try {
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword); // New password
            ps.setString(2, email);       // Email of the user

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Password update error: " + e);
            return false;
        } finally {
            db.closeConnection(conn);
        }
    }
    
    
        // For login purposes
        public boolean Logincredentials(String email, String password) {
            Connection conn = db.openConnection();
            try {
                String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                return rs.next(); // returns true if a record is found
            } catch (Exception e) {
                System.out.println("Sign in error: " + e);
                return false;
            } finally {
                db.closeConnection(conn);
            }
        }
        
        public String Checknames(String email) {
            Connection conn = db.openConnection();
            String fullName = null;

            try {
                String sql = "SELECT first_name, last_name FROM users WHERE email = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    fullName = firstName + " " + lastName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.closeConnection(conn);
            }

            return fullName != null ? fullName : email; // return full name or email if not found
        }
        
        
        
        //updating the value of verified users 
        public boolean verifyAndCleanupUsers() {
            Connection conn = db.openConnection();
            boolean success = false;

            try {
                conn.setAutoCommit(false); // Begin transaction

                // Step 1: Update verified users
                String updateSql = "UPDATE users u "
                        + "JOIN otps o ON u.email = o.email "
                        + "SET u.is_verified = 1";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.executeUpdate();
                }

                // Step 2: Delete unverified users
                String deleteSql = "DELETE FROM users WHERE is_verified = 0";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.executeUpdate();
                }

                conn.commit(); // Commit if both succeed
                success = true;

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (conn != null) {
                        conn.rollback();
                    }
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            } finally {
                db.closeConnection(conn);
            }

            return success;
        }


}
    
