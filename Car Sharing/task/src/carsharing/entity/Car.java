package carsharing.entity;

public class Car {
    private final int id;
    private final String carName;

    public Car(int id, String companyName) {
        this.id = id;
        this.carName = companyName;
    }
    public String getCarName() {
        return carName;
    }

    @Override
    public String toString() {
        return id + ". " + carName;
    }
}
