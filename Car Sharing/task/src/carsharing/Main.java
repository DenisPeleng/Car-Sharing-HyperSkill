package carsharing;

import carsharing.controller.Menu;
import carsharing.dao.*;

public class Main {

    public static void main(String[] args) {
        String databaseName = getDbName(args);
        H2DbController dbController = new H2DbController(databaseName);
        Menu.setDbController(dbController);
        Menu.startMenu();
    }

    private static String getDbName(String[] args) {
        String databaseName = "carsharing";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.contains("-databaseFileName")) {
                databaseName = args[i + 1];
            }
        }
        return databaseName;
    }
}