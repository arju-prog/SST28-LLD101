import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PricingEngine {
    private final Map<SeatType, Double> basePriceMap;
    private final List<PricingStrategy> strategies;

    public PricingEngine(Map<SeatType, Double> basePriceMap, List<PricingStrategy> strategies) {
        this.basePriceMap = Objects.requireNonNull(basePriceMap);
        this.strategies = Objects.requireNonNull(strategies);
    }

    public double calculatePrice(Show show, Seat seat) {
        Double basePrice = basePriceMap.get(seat.getSeatType());
        if (basePrice == null) {
            throw new IllegalArgumentException("No base price configured for seat type: " + seat.getSeatType());
        }
        double price = basePrice;
        for (PricingStrategy strategy : strategies) {
            price = strategy.computePrice(price, show, seat);
        }
        return price;
    }

    public double calculateTotalPrice(Show show, List<Seat> seats) {
        double total = 0;
        for (Seat seat : seats) {
            total += calculatePrice(show, seat);
        }
        return total;
    }
}
