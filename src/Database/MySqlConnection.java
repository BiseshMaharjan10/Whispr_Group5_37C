package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 *
 * @author bibek
 */
public class MySqlConnection implements Database {

    @Override
    public Connection openConnection() {

        try {
            // Replace these with your actual FreeSQLDatabase credentials
            String username = "root"; 
            String password = ""; 
            String database = "whispr"; 

            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/" + database, username, password
            );

            if (connection == null) {
                System.out.println("Database connection fail");
            } else {
                System.out.println("Database connection success");
            }
            return connection;
        } catch (Exception e) {
            System.out.println("Connection Error: " + e);
            return null;
        }

    }

    @Override
    public void closeConnection(Connection conn) {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (Exception e) {
            System.out.println("Close Error: " + e);
        }
    }

    @Override
    public ResultSet runQuery(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Query Error: " + e);
            return null;
        }

    }

    @Override
    public int executeUpdate(Connection conn, String query) {

        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println("Update Error: " + e);
            return -1;
        }
    }
}

