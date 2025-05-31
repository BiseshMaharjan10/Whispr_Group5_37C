
 package Dao;


import Database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Model.UserModel;

import Model.SigninModel;



public class UserDAO {
    private final MySqlConnection db = new MySqlConnection();
    

    public boolean isEmailExists(String email) {
        Connection conn = db.openConnection();
        try {
            String sql = "SELECT * FROM users WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
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
            String sql = "INSERT INTO users (first_name, last_name, password, is_verified, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isVerified());
            ps.setString(5, user.getEmail());

            
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

}
    
