package carsharing.controller;

import carsharing.dao.*;
import carsharing.entity.*;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static CompanyDao companyDao;
    private static CarDao carDao;
    private static H2DbController dbController;

    public static void setDbController(H2DbController dbController) {
        Menu.dbController = dbController;
        companyDao = new CompanyDaoImpl(dbController.getConnection());
        carDao = new CarDaoImpl(dbController.getConnection());
    }

    public static void startMenu() {
        boolean isStartMenuShowing = true;
        while (isStartMenuShowing) {
            MenuChooseOptions.showStarMenu();
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> showManagerMenu();
                case "0" -> isStartMenuShowing = false;
                default -> System.out.println("Wrong parameter");
            }

        }
    }

    public static void showManagerMenu() {
        boolean isMangerMenuShowing = true;
        while (isMangerMenuShowing) {
            MenuChooseOptions.showManagerMenu();
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> {
                    List<Company> resultList = companyDao.getAllCompanies();
                    if (resultList.isEmpty()) {
                        System.out.println("The company list is empty!");
                    } else {
                        System.out.println("Choose the company:");
                        resultList.forEach(System.out::println);
                        System.out.println("0. Back");
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

    public static void showCompanyMenu(int idCompany) {
        boolean isCompanyMenuShowing = true;
        while (isCompanyMenuShowing) {
            Company currentCompany = companyDao.getCompany(idCompany);
            System.out.printf("'%s' company\n", currentCompany.getName());
            MenuChooseOptions.showCompanyMenu();
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> {
                    List<Car> resultList = carDao.getAllCars(idCompany);
                    if (resultList.isEmpty()) {
                        System.out.println("The car list is empty!");
                    } else {
                        System.out.println("Car list:");
                        int tempId = 1;
                        for (Car currentCar : resultList
                        ) {
                            System.out.println(tempId + ". " + currentCar.getCarName());
                            tempId++;
                        }
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
}
