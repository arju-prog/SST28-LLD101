import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieTicketBookingController {
    private final Map<City, List<Theatre>> cityTheatreMap;
    private final BookingService bookingService;

    public MovieTicketBookingController(Map<City, List<Theatre>> cityTheatreMap, BookingService bookingService) {
        this.cityTheatreMap = Objects.requireNonNull(cityTheatreMap);
        this.bookingService = Objects.requireNonNull(bookingService);
    }

    public List<Theatre> showTheatres(City city) {
        return cityTheatreMap.getOrDefault(city, Collections.emptyList());
    }

    public List<Movie> showMovies(City city) {
        List<Theatre> theatres = cityTheatreMap.getOrDefault(city, Collections.emptyList());
        return theatres.stream()
                .flatMap(t -> t.getCurrentMovies().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Theatre> getTheatresForMovie(City city, Movie movie) {
        List<Theatre> theatres = cityTheatreMap.getOrDefault(city, Collections.emptyList());
        return theatres.stream()
                .filter(t -> t.getShowsForMovie(movie).size() > 0)
                .collect(Collectors.toList());
    }

    public List<Show> getShowsForMovie(Theatre theatre, Movie movie) {
        return theatre.getShowsForMovie(movie);
    }

    public MovieTicket bookTickets(Show show, List<Seat> seats) {
        return bookingService.bookTickets(show, seats);
    }

    public double cancelBooking(MovieTicket ticket) {
        return bookingService.cancelBooking(ticket);
    }
}
