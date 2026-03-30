import java.util.Objects;

public class Seat {
    private final String seatId;
    private final int rowNumber;
    private final int seatNumber;
    private final SeatType seatType;

    public Seat(String seatId, int rowNumber, int seatNumber, SeatType seatType) {
        this.seatId = Objects.requireNonNull(seatId);
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatType = Objects.requireNonNull(seatType);
    }

    public String getSeatId() {
        return seatId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    @Override
    public String toString() {
        return "Seat{" + seatId + ", R" + rowNumber + "-S" + seatNumber + ", " + seatType + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;
        Seat seat = (Seat) o;
        return seatId.equals(seat.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId);
    }
}
