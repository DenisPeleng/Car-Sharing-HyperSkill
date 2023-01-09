package carsharing.dao;

import carsharing.entity.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl extends GeneralDao implements CompanyDao {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS COMPANY (
            ID INTEGER AUTO_INCREMENT PRIMARY KEY,
            NAME VARCHAR(255) UNIQUE NOT NULL)
            """;
    private static final String GET_ALL_COMPANIES = "SELECT * FROM COMPANY";
    private static final String CREATE_COMPANY = "INSERT INTO COMPANY (NAME) VALUES(?)";
    private static final String GET_COMPANY = "SELECT * FROM COMPANY WHERE ID = ?";
    private final Connection connection;

    public CompanyDaoImpl(Connection connection) {
        super(connection, CREATE_TABLE_SQL);
        this.connection = connection;
    }
    @Override
    public void createCompany(String companyName) {
        try (PreparedStatement prepStmt = connection.prepareStatement(CREATE_COMPANY)) {
            prepStmt.setString(1, companyName);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Company getCompany(int id) {
        Company currentCompany = null;
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_COMPANY)) {
            prepStmt.setInt(1, id);
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                currentCompany = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentCompany;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> allCompaniesList = new ArrayList<>();
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_ALL_COMPANIES)) {
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                allCompaniesList.add(new Company(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCompaniesList;
    }
}
