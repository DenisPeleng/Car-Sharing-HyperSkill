package carsharing.dao;

import carsharing.entity.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl extends GeneralDao implements CarDao {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS CAR (
            ID INTEGER AUTO_INCREMENT PRIMARY KEY,
            NAME VARCHAR(255) UNIQUE NOT NULL,
            COMPANY_ID INT NOT NULL,
            CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)
            );
            """;
    private static final String GET_ALL_CARS = "SELECT * FROM CAR WHERE COMPANY_ID = ?";
    private static final String CREATE_CAR = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES(?,?)";
    private static final String GET_CAR = "SELECT * FROM CAR WHERE COMPANY_ID = ?";
    private final Connection connection;

    public CarDaoImpl(Connection connection) {
        super(connection, CREATE_TABLE_SQL);
        this.connection = connection;
    }

    @Override
    public void createCar(String carName, int idCompany) {
        try (PreparedStatement prepStmt = connection.prepareStatement(CREATE_CAR)) {
            prepStmt.setString(1, carName);
            prepStmt.setInt(2, idCompany);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCar(int id) {
        Car currentCar = null;
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_CAR)) {
            prepStmt.setInt(1, id);
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                currentCar = new Car(resultSet.getInt("ID"), resultSet.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currentCar;
    }

    @Override
    public List<Car> getAllCars(int idCompany) {
        List<Car> allCarsList = new ArrayList<>();
        try (PreparedStatement prepStmt = connection.prepareStatement(GET_ALL_CARS)) {
            prepStmt.setInt(1, idCompany);
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                allCarsList.add(new Car(resultSet.getInt("ID"), resultSet.getString("NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCarsList;
    }
}
