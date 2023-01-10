package carsharing.dao;

import carsharing.entity.Car;
import carsharing.entity.Customer;

import java.util.List;

public interface CustomerDao {
    void createCustomer(String customerName);

    List<Customer> getAllCustomers();

    Car getRentedCar(Customer customer);

    Customer getCustomer(int idCustomer);

    void rentCar(Customer customer, int idCar);

    boolean isRentedCar(Customer customer);

    void returnRentedCar(Customer customer);
}
