package Database;
import java.sql.*;

/**
 *
 * @author bibek
 */
public interface Database {
    Connection openConnection();
    void closeConnection(Connection conn);
    ResultSet runQuery(Connection conn,String query);
    int executeUpdate(Connection conn,String query);
    
}
