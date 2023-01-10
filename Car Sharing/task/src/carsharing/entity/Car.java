package carsharing.entity;

public class Car {

    private final int id;
    private final String carName;
    private final int companyId;

    public Car(int id, String carName, int companyId) {
        this.id = id;
        this.carName = carName;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getCarName() {
        return carName;
    }

    @Override
    public String toString() {
        return id + ". " + carName;
    }
}
