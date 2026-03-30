import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MovieTicket {
    private final String ticketId;
    private final Show show;
    private final List<Seat> bookedSeats;
    private final double totalPrice;
    private final LocalDateTime bookingTime;
    private BookingStatus status;

    private MovieTicket(Builder builder) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.show = builder.show;
        this.bookedSeats = Collections.unmodifiableList(builder.bookedSeats);
        this.totalPrice = builder.totalPrice;
        this.bookingTime = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "MovieTicket{" + ticketId
                + ", show=" + show.getMovie().getTitle()
                + ", seats=" + bookedSeats.size()
                + ", price=" + String.format("%.2f", totalPrice)
                + ", status=" + status + "}";
    }

    public static class Builder {
        private Show show;
        private List<Seat> bookedSeats;
        private double totalPrice;

        public Builder show(Show show) {
            this.show = show;
            return this;
        }

        public Builder bookedSeats(List<Seat> bookedSeats) {
            this.bookedSeats = bookedSeats;
            return this;
        }

        public Builder totalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public MovieTicket build() {
            Objects.requireNonNull(show, "Show is required");
            Objects.requireNonNull(bookedSeats, "Seats are required");
            if (bookedSeats.isEmpty()) {
                throw new IllegalArgumentException("At least one seat must be booked");
            }
            return new MovieTicket(this);
        }
    }
}
