package carsharing.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DbController {
    private Connection connection;
    private final String databaseName;

    public H2DbController(String dbName) {
        this.databaseName = dbName;
    }


    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                System.out.println("Can't close connection to the database.");
            }
            connection = null;
        }
    }

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {

            String databaseFilePath = "./src/carsharing/db/";
            connection = DriverManager.getConnection("jdbc:h2:" + databaseFilePath + databaseName);
        } catch (SQLException ignored) {
            System.out.println("Can't connect to the database.");
        }
        return connection;
    }
}
