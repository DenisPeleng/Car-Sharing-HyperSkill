package carsharing.controller;

public class MenuChooseOptions {
    public static void showStarMenuOptions() {
        System.out.println("""
                1. Log in as a manager
                2. Log in as a customer
                3. Create a customer
                0. Exit""");
    }

    public static void showManagerMenuOptions() {
        System.out.println("""
                1. Company list
                2. Create a company
                0. Back""");
    }

    public static void showCompanyMenuOptions() {
        System.out.println("""
                1. Car list
                2. Create a car
                0. Back""");
    }

    public static void showCustomerMenuOptions() {
        System.out.println("""
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back""");
    }

}
