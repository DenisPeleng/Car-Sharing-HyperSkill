package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/";

    static final String USER = "";
    static final String PASS = "";

    public static void main(String[] args) {
        String databaseName = "db";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.contains("-databaseFileName")) {
                databaseName = args[i + 1];

            }
            Connection conn = null;
            Statement stmt = null;
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL+databaseName, USER, PASS);
                conn.setAutoCommit(true);
                stmt = conn.createStatement();
                String sql = "CREATE TABLE   COMPANY " +
                             "(ID INTEGER not NULL, " +
                             " NAME VARCHAR(255))";
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
            } catch (Exception se) {
                se.printStackTrace();
            } finally {
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException ignored) {
                }
                try {
                    if (conn != null) conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
}