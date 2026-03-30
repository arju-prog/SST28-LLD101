import java.util.List;
import java.util.Objects;

public class BookingService {
    private final PricingEngine pricingEngine;

    public BookingService(PricingEngine pricingEngine) {
        this.pricingEngine = Objects.requireNonNull(pricingEngine);
    }

    public MovieTicket bookTickets(Show show, List<Seat> seats) {
        boolean locked = show.lockSeats(seats);
        if (!locked) {
            throw new RuntimeException("Seats not available for show: " + show.getShowId());
        }
        double totalPrice = pricingEngine.calculateTotalPrice(show, seats);
        return new MovieTicket.Builder()
                .show(show)
                .bookedSeats(seats)
                .totalPrice(totalPrice)
                .build();
    }

    public double cancelBooking(MovieTicket ticket) {
        ticket.getShow().releaseSeats(ticket.getBookedSeats());
        ticket.cancel();
        return ticket.getTotalPrice();
    }
}
