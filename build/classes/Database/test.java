


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