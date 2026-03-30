import java.time.DayOfWeek;

public class WeekendSurgePricing implements PricingStrategy {

    private static final double SURGE_MULTIPLIER = 1.5;

    @Override
    public double computePrice(double basePrice, Show show, Seat seat) {
        DayOfWeek day = show.getStartTime().getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return basePrice * SURGE_MULTIPLIER;
        }
        return basePrice;
    }
}
