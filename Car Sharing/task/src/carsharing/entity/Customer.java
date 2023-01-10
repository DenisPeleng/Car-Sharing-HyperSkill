package carsharing.entity;

public class Customer {
    private final int id;
    private final String customerName;

    public Customer(String customerName, int id) {
        this.customerName = customerName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ". " + customerName;
    }
}
