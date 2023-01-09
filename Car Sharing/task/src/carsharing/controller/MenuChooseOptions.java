package carsharing.controller;

public class MenuChooseOptions {
    public static void showStarMenu() {
        System.out.println("""
                1. Log in as a manager
                0. Exit""");
    }

    public static void showManagerMenu() {
        System.out.println("""
                1. Company list
                2. Create a company
                0. Back""");
    }

    public static void showCompanyMenu() {
        System.out.println("""
                1. Car list
                2. Create a car
                0. Back""");
    }
}
