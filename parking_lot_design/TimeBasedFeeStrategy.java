package parking_system;

import java.util.Map;

public class TimeBasedFeeStrategy implements FeeCalculationStrategy {
    private Map<SpaceSize, Double> rateChart;

    public TimeBasedFeeStrategy(Map<SpaceSize, Double> rates) {
        this.rateChart = rates;
    }

    @Override
    public double computeCharge(ParkingToken token, long departureTimeMs) {
        long durationSpent = departureTimeMs - token.getCheckInTime();
        double hoursElapsed = Math.ceil(durationSpent / (1000.0 * 60 * 60));
        if (hoursElapsed == 0)
            hoursElapsed = 1;

        double currentRate = rateChart.getOrDefault(token.getAssignedSpace().getSizeCategory(), 10.0);
        return hoursElapsed * currentRate;
    }
}
