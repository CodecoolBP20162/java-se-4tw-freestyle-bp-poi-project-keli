package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcDao {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/poi_project",
                "keli", "keligeri");
    }

    public void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement())
        {
            System.out.println(query);
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
