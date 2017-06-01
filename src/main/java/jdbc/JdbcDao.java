package jdbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


/**
 * {@link JdbcDao} class responsible for config file reading
 * and setup the required params for the connection.
 * <p>
 * Read the necessary parameter from the config gile (resources/connection.properties)
 * and setup the parsed parameter for the correct variables.
 *
 * @author gem
 * @version 1.8
 */
public class JdbcDao {

    private static String filePath = "src/main/resources/";
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void setupUserAndPasswordFromFile(String fileName) {
        try {
            List<String> allLinesList = Files.readAllLines(Paths.get(filePath + fileName));
            dbUrl = "jdbc:postgresql://" + allLinesList.get(0) + "/" + allLinesList.get(1);
            dbUser = allLinesList.get(2);
            dbPassword = allLinesList.get(3);
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
