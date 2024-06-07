package w10;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    static final String DB_URL = "jdbc:mysql://localhost/coursemanagementsystem";
    static final String USERNAME = "root";
    static final String PASSWORD = "";


    
    public static Connection connect() throws SQLException {
        // Establishing a connection
        
    	Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    	return connection;
        
    }
    
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}