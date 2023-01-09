package carsharing.dao;

import carsharing.entity.Car;

import java.util.List;

public interface CarDao {
    void createCar(String carName, int idCompany);
    Car getCar(int id);
    List<Car> getAllCars(int idCompany);

}
