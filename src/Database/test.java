/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;
import Database.MySqlConnection;
import java.sql.Connection;
import Database.Database;

public class test {
    public static void main(String[] args) {
        Database db = new MySqlConnection(); // Create object
        Connection conn = db.openConnection(); // This triggers the message
    }
}