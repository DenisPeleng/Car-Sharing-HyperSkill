package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDatabase {
    Connection connection;
    private final String databaseName;

    CompaniesDatabase(String dbName) {
        this.databaseName = dbName;
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

    public void createTableIfNotExist() {
        String statement = "CREATE TABLE IF NOT EXISTS COMPANY " +
                           "(ID INTEGER auto_increment primary key , " +
                           "NAME VARCHAR(255) UNIQUE NOT NULL)";
        try {
            getConnection().prepareStatement(statement).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void createCompany(String companyName) {

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO COMPANY (NAME) VALUES (?)");
            preparedStatement.setString(1, companyName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCompaniesList() {
        List<String> resultList = getCompaniesList();
        if (resultList.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Company list:");
            resultList.forEach(System.out::println);
        }
    }

    private List<String> getCompaniesList() {
        createTableIfNotExist();
        List<String> resultList = new ArrayList<>();
        String statement = "SELECT * FROM COMPANY";
        ResultSet resultSet;
        try {
            resultSet = getConnection().prepareStatement(statement).executeQuery();
            while (resultSet.next()) {
                resultList.add(resultSet.getString("ID") + ". " + resultSet.getString("NAME"));
            }
            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
