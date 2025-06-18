package Dao;

import Model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Database.MySqlConnection;

public class ChatClientDAO {
    private final MySqlConnection db = new MySqlConnection();

    public List<Message> getAllUsers() {
        List<Message> userList = new ArrayList<>();
        String sql = "SELECT first_name, last_name FROM users";
        Connection conn = db.openConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Message user = new Message(); // If you meant User, rename your model
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
    
    
    
    
}