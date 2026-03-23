package parking_system;

public class Automobile {
    private String regNumber;
    private AutoCategory category;

    public Automobile(String regPlate, AutoCategory cat) {
        this.regNumber = regPlate;
        this.category = cat;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public AutoCategory getCategory() {
        return category;
    }
}
