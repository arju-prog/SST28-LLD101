public interface PricingStrategy {
    double computePrice(double basePrice, Show show, Seat seat);
}
