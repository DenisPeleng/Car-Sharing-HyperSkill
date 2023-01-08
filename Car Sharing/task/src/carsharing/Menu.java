package carsharing;

import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void startMenu(CompaniesDatabase companiesDatabase) {
        boolean isStartMenuShowing = true;
        while (isStartMenuShowing) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> showManagerMenu(companiesDatabase);
                case "0" -> isStartMenuShowing = false;
                default -> System.out.println("Wrong command");
            }

        }
    }

    public static void showManagerMenu(CompaniesDatabase companiesDatabase) {
        boolean isMangerMenuShowing = true;
        while (isMangerMenuShowing) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "1" -> {
                    companiesDatabase.showCompaniesList();
                    System.out.println();
                }
                case "2" -> {
                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();
                    companiesDatabase.createCompany(companyName);
                    System.out.println("The company was created!");
                    System.out.println();

                }
                case "0" -> isMangerMenuShowing = false;
                default -> System.out.println("Wrong command");
            }
        }

    }
}
