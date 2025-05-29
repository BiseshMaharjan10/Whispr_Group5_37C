package Dao;

import Database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Model.UserModel;


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


    public boolean registerUser(UserModel user) {
        Connection conn = db.openConnection();
        try {
            String sql = "INSERT INTO users (first_name, last_name, password, is_verified, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setBoolean(4, user.isVerified());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Registration error: " + e);
            return false;
        } finally {
            db.closeConnection(conn);
        }
    }
}
    
