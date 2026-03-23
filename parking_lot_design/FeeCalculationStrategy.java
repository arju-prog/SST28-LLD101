package parking_system;

public interface FeeCalculationStrategy {
    double computeCharge(ParkingToken token, long departureTimeMs);
}
