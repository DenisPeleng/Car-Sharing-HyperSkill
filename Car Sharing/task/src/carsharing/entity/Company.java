package carsharing.entity;

public class Company {
    private final int id;
    private final String companyName;

    public Company(int id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return companyName;
    }

    @Override
    public String toString() {
        return id + ". " + companyName;
    }
}
