import java.util.Objects;

public class City {
    private final String cityId;
    private final String name;

    public City(String cityId, String name) {
        this.cityId = Objects.requireNonNull(cityId);
        this.name = Objects.requireNonNull(name);
    }

    public String getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "City{" + name + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return cityId.equals(city.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId);
    }
}
