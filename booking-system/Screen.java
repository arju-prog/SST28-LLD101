import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Screen {
    private final String screenId;
    private final List<Seat> seats;

    public Screen(String screenId, List<Seat> seats) {
        this.screenId = Objects.requireNonNull(screenId);
        this.seats = Collections.unmodifiableList(seats);
    }

    public String getScreenId() {
        return screenId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return "Screen{" + screenId + ", seats=" + seats.size() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screen)) return false;
        Screen screen = (Screen) o;
        return screenId.equals(screen.screenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screenId);
    }
}
