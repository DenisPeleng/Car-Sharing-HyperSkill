package carsharing.controller;

import carsharing.dao.*;
import carsharing.entity.*;

import java.util.List;
import java.util.Scanner;

import static carsharing.controller.MenuChooseOptions.*;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static CompanyDao companyDao;
    private static CarDao carDao;
    private static CustomerDao customerDao;
    private static H2DbController dbController;

    public static void setDbController(H2DbController dbController) {
        Menu.dbController = dbController;
        companyDao = new CompanyDaoImpl(dbController.getConnection());
        carDao = new CarDaoImpl(dbController.getConnection());
        customerDao = new CustomerDaoImpl(dbController.getConnection());
    }

    public static void startMenu() {
        boolean isStartMenuShowing = true;
        while (isStartMenuShowing) {
            showStarMenuOptions();
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> showManagerMenu();
                case "2" -> showCustomersChooseMenu();
                case "3" -> {
                    System.out.println("Enter the customer name:");
                    String customerName = scanner.nextLine();
                    customerDao.createCustomer(customerName);
                    System.out.println("The customer was added!\n");
                }
                case "0" -> isStartMenuShowing = false;
                default -> System.out.println("Wrong parameter");
            }

        }
    }

    public static void showManagerMenu() {
        boolean isMangerMenuShowing = true;
        while (isMangerMenuShowing) {
            showManagerMenuOptions();
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> {
                    if (printAllCompanies()) {
                        int idCompany = Integer.parseInt(scanner.nextLine());
                        if (idCompany != 0) {
                            System.out.println();
                            showCompanyMenu(idCompany);
                        }
                    }

                }
                case "2" -> {
                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();
                    companyDao.createCompany(companyName);
                    System.out.println("The company was created!");
                    System.out.println();

                }
                case "0" -> {
                    isMangerMenuShowing = false;
                    dbController.closeConnection();
                }
                default -> System.out.println("Wrong parameter");
            }
        }

    }

    private static boolean printAllCompanies() {
        List<Company> resultList = companyDao.getAllCompanies();
        if (resultList.isEmpty()) {
            System.out.println("The company list is empty!");
            return false;
        } else {
            System.out.println("Choose the company:");
            resultList.forEach(System.out::println);
            System.out.println("0. Back");
            return true;
        }
    }

    public static void showCompanyMenu(int idCompany) {
        boolean isCompanyMenuShowing = true;
        while (isCompanyMenuShowing) {
            Company currentCompany = companyDao.getCompany(idCompany);
            System.out.printf("'%s' company\n", currentCompany.getName());
            showCompanyMenuOptions();
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> {
                    List<Car> resultList = carDao.getAllCars(idCompany);
                    if (resultList.isEmpty()) {
                        System.out.println("The car list is empty!");
                    } else {
                        printCarsList(resultList);
                    }
                }
                case "2" -> {
                    System.out.println("Enter the car name:");
                    String carName = scanner.nextLine();
                    carDao.createCar(carName, currentCompany.getId());
                    System.out.println("The car was created!");
                }
                case "0" -> isCompanyMenuShowing = false;
                default -> System.out.println("Wrong parameter");
            }
            System.out.println();
        }
    }

    private static void printCarsList(List<Car> resultList) {
        System.out.println("Car list:");
        int tempId = 1;
        for (Car currentCar : resultList
        ) {
            System.out.println(tempId + ". " + currentCar.getCarName());
            tempId++;
        }
    }

    public static void showCustomersChooseMenu() {
        boolean isCustomersMenuWorking = true;
        while (isCustomersMenuWorking) {
            List<Customer> resultList = customerDao.getAllCustomers();
            if (resultList.isEmpty()) {
                System.out.println("The customer list is empty!");
                isCustomersMenuWorking = false;
            } else {
                System.out.println("Choose a customer:");
                resultList.forEach(System.out::println);
                System.out.println("0. Back");
                int idCustomer = Integer.parseInt(scanner.nextLine());
                if (idCustomer != 0) {
                    System.out.println();
                    showCustomerMenu(idCustomer);
                } else {
                    isCustomersMenuWorking = false;
                }
            }
        }

    }

    public static void showCustomerMenu(int idCustomer) {
        boolean isCustomerMenuWorking = true;
        while (isCustomerMenuWorking) {
            showCustomerMenuOptions();
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> {
                    Customer currentCustomer = customerDao.getCustomer(idCustomer);
                    if (customerDao.isRentedCar(currentCustomer)) {
                        System.out.println("You've already rented a car!\n");
                    } else {
                        boolean isChoosingCompany = true;
                        while (isChoosingCompany) {
                            if (printAllCompanies()) {
                                int idCompany = Integer.parseInt(scanner.nextLine());
                                if (idCompany != 0) {
                                    System.out.println();
                                    boolean result;
                                    List<Car> resultCarList = carDao.getAllCars(idCompany);
                                    if (resultCarList.isEmpty()) {
                                        System.out.printf("No available cars in the '%s' company\n\n", companyDao.getCompany(idCompany));
                                        result = false;
                                    } else {
                                        printCarsList(resultCarList);
                                        result = true;
                                    }
                                    if (result) {
                                        int idCarToRent = Integer.parseInt(scanner.nextLine());
                                        if (idCarToRent != 0) {
                                            Car carToRent = resultCarList.get(idCarToRent - 1);
                                            customerDao.rentCar(currentCustomer, carToRent.getId());
                                            System.out.printf("You rented '%s'\n\n", carToRent.getCarName());
                                            isChoosingCompany = false;
                                        }

                                    }

                                }
                            } else {
                                isChoosingCompany = false;
                            }
                        }
                    }
                }
                case "2" -> {
                    Customer currentCustomer = customerDao.getCustomer(idCustomer);
                    if (!customerDao.isRentedCar(currentCustomer)) {
                        System.out.println("You didn't rent a car!\n");
                    } else {
                        customerDao.returnRentedCar(currentCustomer);
                        System.out.println("You've returned a rented car!\n");
                    }
                }
                case "3" -> {
                    Customer currentCustomer = customerDao.getCustomer(idCustomer);
                    if (customerDao.isRentedCar(currentCustomer)) {
                        Car rentedCar = customerDao.getRentedCar(currentCustomer);
                        System.out.println("Your rented car:");
                        System.out.println(rentedCar.getCarName());
                        System.out.println("Company:");
                        Company currentCompany = companyDao.getCompany(rentedCar.getCompanyId());
                        System.out.println(currentCompany.getName());
                        System.out.println();
                    } else {
                        System.out.println("You didn't rent a car!\n");
                    }

                }
                case "0" -> isCustomerMenuWorking = false;
            }
        }
    }

}
