package carsharing;

public class Main {

    public static void main(String[] args) {
        String databaseName = "carsharing";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.contains("-databaseFileName")) {
                databaseName = args[i + 1];
            }
        }
        CompaniesDatabase companiesDatabase = new CompaniesDatabase(databaseName);
        companiesDatabase.createTableIfNotExist();
        Menu.startMenu(companiesDatabase);
    }
}