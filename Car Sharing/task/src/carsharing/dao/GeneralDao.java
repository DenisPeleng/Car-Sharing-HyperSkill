package carsharing.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class GeneralDao {
    Connection connection;
    private final String createTableSql;

    GeneralDao(Connection connection, String createTableSql) {
        this.connection = connection;
        this.createTableSql = createTableSql;
        createTableIfNotExist();
    }

    private void createTableIfNotExist() {
        try {
            connection.prepareStatement(createTableSql).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
