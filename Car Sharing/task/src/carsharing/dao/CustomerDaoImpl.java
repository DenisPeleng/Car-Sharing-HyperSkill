package carsharing.dao;

import carsharing.entity.Car;
import carsharing.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl extends GeneralDao implements CustomerDao {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS CUSTOMER (
            ID INTEGER AUTO_INCREMENT PRIMARY KEY,
            NAME VARCHAR(255) UNIQUE NOT NULL,
            RENTED_CAR_ID INT DEFAULT NULL,
            CONSTRAINT FK_RENTED_CAR FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)
            )
            """;
    private static final String GET_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER";
    private static final String GET_RENTED_CAR = """
            SELECT CUSTOMER.RENTED_CAR_ID as RENT_ID, CAR.NAME as CAR_NAME, CAR.COMPANY_ID COMPANY_ID
            FROM CUSTOMER
            JOIN CAR on CAR.ID = CUSTOMER.RENTED_CAR_ID
            WHERE CUSTOMER.ID = ?;""";
    private static final String RETURN_RENTED_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";
    private static final String CREATE_CUSTOMER = "INSERT INTO CUSTOMER (NAME) VALUES(?)";
    private static final String GET_CUSTOMER = "SELECT * FROM CUSTOMER WHERE ID = ?";
    private static final String SET_RENTED_CAR = "UPDATE CUSTOMER SET RENTED_CAR_ID = ?  WHERE ID = ?";
    private final Connection connection;

    public CustomerDaoImpl(Connection connection) {
        super(connection, CREATE_TABLE_SQL);
        this.connection = connection;
    }

    @Override
    public void createCustomer(String customerName) {
        try (PreparedStatement prepStmt = connection.prepareStatement(CREATE_CUSTOMER)) {
            prepStmt.setString(1, customerName);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomersList = new ArrayList<>();
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_ALL_CUSTOMERS)) {
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                allCustomersList.add(new Customer(resultSet.getString("NAME"), resultSet.getInt("ID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomersList;
    }

    @Override
    public Car getRentedCar(Customer customer) {
        Car currentCar = null;
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_RENTED_CAR)) {
            prepStmt.setInt(1, customer.getId());
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                currentCar = new Car(resultSet.getInt("RENT_ID"), resultSet.getString("CAR_NAME"), resultSet.getInt("COMPANY_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentCar;
    }

    @Override
    public Customer getCustomer(int idCustomer) {
        Customer currentCustomer = null;
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_CUSTOMER)) {
            prepStmt.setInt(1, idCustomer);
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                currentCustomer = new Customer(resultSet.getString("NAME"), resultSet.getInt("ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentCustomer;
    }

    @Override
    public void rentCar(Customer customer, int idCar) {
        try (PreparedStatement prepStmt = connection.prepareStatement(SET_RENTED_CAR)) {
            prepStmt.setInt(1, idCar);
            prepStmt.setInt(2, customer.getId());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRentedCar(Customer customer) {
        int rentedCarId = 0;
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_CUSTOMER)) {
            prepStmt.setInt(1, customer.getId());
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                rentedCarId = resultSet.getInt("RENTED_CAR_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentedCarId!=0;
    }

    @Override
    public void returnRentedCar(Customer customer) {
        try (PreparedStatement prepStmt = connection.prepareStatement(RETURN_RENTED_CAR)) {
            prepStmt.setInt(1, customer.getId());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
