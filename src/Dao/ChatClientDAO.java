package Dao;

import Model.MessageModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Database.MySqlConnection;

public class ChatClientDAO {
    private final MySqlConnection db = new MySqlConnection();

    public List<MessageModel> getAllUsers() {
        List<MessageModel> userList = new ArrayList<>();
        String sql = "SELECT first_name, last_name FROM users";
        Connection conn = db.openConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MessageModel user = new MessageModel(); // If you meant User, rename your model
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }

        return userList;
    }
    
    
    
    public String getEmail(String firstName, String lastName) {
        Connection conn = db.openConnection();
        try {
            String sql = "SELECT email FROM users WHERE first_name=? and last_name = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("email"); // return the found OTP code
            } else {
                return null; // OTP not found
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            db.closeConnection(conn);
        }
    }
    
    
    public String getFirstnLastName(String email) {
        Connection conn = db.openConnection();
        try {
            String sql = "SELECT first_name, last_name FROM users WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                return firstName + " " + lastName;// return the found OTP code
            } else {
                return null; // OTP not found
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            db.closeConnection(conn);
        }
    }
    
    
    
}