package carsharing.dao;

import carsharing.entity.Car;

import java.util.List;

public interface CarDao {
    void createCar(String carName, int idCompany);
    List<Car> getAllCars(int idCompany);

}
