public class NightSurgePricing implements PricingStrategy {

    private static final int NIGHT_HOUR = 21;
    private static final double SURGE_MULTIPLIER = 1.2;

    @Override
    public double computePrice(double basePrice, Show show, Seat seat) {
        if (show.getStartTime().getHour() >= NIGHT_HOUR) {
            return basePrice * SURGE_MULTIPLIER;
        }
        return basePrice;
    }
}
