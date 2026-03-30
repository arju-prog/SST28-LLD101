import java.util.Objects;

public class Movie {
    private final String movieId;
    private final String title;
    private final int durationMinutes;

    public Movie(String movieId, String title, int durationMinutes) {
        this.movieId = Objects.requireNonNull(movieId);
        this.title = Objects.requireNonNull(title);
        this.durationMinutes = durationMinutes;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public String toString() {
        return "Movie{" + title + ", " + durationMinutes + "min}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return movieId.equals(movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
