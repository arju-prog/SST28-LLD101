import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Show {
    private final String showId;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final Map<Seat, Boolean> seatAvailability;
    private final Object lock = new Object();

    public Show(String showId, Movie movie, Screen screen, LocalDateTime startTime) {
        this.showId = Objects.requireNonNull(showId);
        this.movie = Objects.requireNonNull(movie);
        this.screen = Objects.requireNonNull(screen);
        this.startTime = Objects.requireNonNull(startTime);
        this.seatAvailability = new HashMap<>();
        for (Seat seat : screen.getSeats()) {
            seatAvailability.put(seat, true);
        }
    }

    public String getShowId() {
        return showId;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public List<Seat> getAvailableSeats() {
        synchronized (lock) {
            return seatAvailability.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    public boolean lockSeats(List<Seat> seats) {
        synchronized (lock) {
            for (Seat seat : seats) {
                Boolean available = seatAvailability.get(seat);
                if (available == null || !available) {
                    return false;
                }
            }
            for (Seat seat : seats) {
                seatAvailability.put(seat, false);
            }
            return true;
        }
    }

    public void releaseSeats(List<Seat> seats) {
        synchronized (lock) {
            for (Seat seat : seats) {
                seatAvailability.put(seat, true);
            }
        }
    }

    @Override
    public String toString() {
        return "Show{" + showId + ", " + movie.getTitle() + ", " + screen.getScreenId()
                + ", " + startTime + "}";
    }
}
