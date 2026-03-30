import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Theatre {
    private final String theatreId;
    private final String name;
    private final City city;
    private final List<Screen> screens;
    private final List<Show> shows;
    private final Object showsLock = new Object();

    public Theatre(String theatreId, String name, City city, List<Screen> screens) {
        this.theatreId = Objects.requireNonNull(theatreId);
        this.name = Objects.requireNonNull(name);
        this.city = Objects.requireNonNull(city);
        this.screens = Collections.unmodifiableList(screens);
        this.shows = new ArrayList<>();
    }

    public void addShow(Show show) {
        synchronized (showsLock) {
            boolean screenBelongs = screens.stream()
                    .anyMatch(s -> s.getScreenId().equals(show.getScreen().getScreenId()));
            if (!screenBelongs) {
                throw new IllegalArgumentException("Screen " + show.getScreen().getScreenId()
                        + " does not belong to theatre " + theatreId);
            }

            boolean hasOverlap = shows.stream()
                    .filter(s -> s.getScreen().getScreenId().equals(show.getScreen().getScreenId()))
                    .anyMatch(existing -> {
                        long existingEnd = existing.getStartTime().plusMinutes(existing.getMovie().getDurationMinutes()).toEpochSecond(java.time.ZoneOffset.UTC);
                        long existingStart = existing.getStartTime().toEpochSecond(java.time.ZoneOffset.UTC);
                        long newEnd = show.getStartTime().plusMinutes(show.getMovie().getDurationMinutes()).toEpochSecond(java.time.ZoneOffset.UTC);
                        long newStart = show.getStartTime().toEpochSecond(java.time.ZoneOffset.UTC);
                        return newStart < existingEnd && newEnd > existingStart;
                    });
            if (hasOverlap) {
                throw new IllegalArgumentException("Show overlaps with existing show on screen "
                        + show.getScreen().getScreenId());
            }

            shows.add(show);
        }
    }

    public List<Show> getShowsForMovie(Movie movie) {
        synchronized (showsLock) {
            return shows.stream()
                    .filter(s -> s.getMovie().equals(movie))
                    .collect(Collectors.toList());
        }
    }

    public List<Movie> getCurrentMovies() {
        synchronized (showsLock) {
            return shows.stream()
                    .map(Show::getMovie)
                    .distinct()
                    .collect(Collectors.toList());
        }
    }

    public List<Show> getShows() {
        synchronized (showsLock) {
            return Collections.unmodifiableList(new ArrayList<>(shows));
        }
    }

    public String getTheatreId() {
        return theatreId;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    @Override
    public String toString() {
        return "Theatre{" + name + ", " + city.getName() + "}";
    }
}
