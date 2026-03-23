package parking_system;

import java.util.HashMap;
import java.util.Map;

public class ApplicationRunner {
    public static void main(String[] args) {
        Map<SpaceSize, Double> feeBoard = new HashMap<>();
        feeBoard.put(SpaceSize.COMPACT, 6.0);
        feeBoard.put(SpaceSize.STANDARD, 12.0);
        feeBoard.put(SpaceSize.OVERSIZED, 25.0);
        FeeCalculationStrategy feeLogic = new TimeBasedFeeStrategy(feeBoard);

        SpotAllocationStrategy logic = new ClosestSpotStrategy();

        ParkingFacility theFacility = new ParkingFacility(logic, feeLogic);

        AccessPoint portalOne = new AccessPoint("Portal-A", 1, 0, 0);
        theFacility.registerGate(portalOne);

        theFacility.registerSpace(new ParkingSpace("SP-101", SpaceSize.STANDARD, 1, 10, 10));
        theFacility.registerSpace(new ParkingSpace("SP-102", SpaceSize.STANDARD, 1, 50, 50));
        theFacility.registerSpace(new ParkingSpace("SP-201", SpaceSize.COMPACT, 2, 5, 5));

        theFacility.printFacilityStatus(null);

        Automobile myCar = new Automobile("XYZ-9999", AutoCategory.SEDAN);
        ParkingToken token = theFacility.admitVehicle(myCar, portalOne);

        if (token != null) {
            long threeHoursPast = System.currentTimeMillis() - (3 * 60 * 60 * 1000) - 1000;
            token.alterCheckInTime(threeHoursPast);
            theFacility.checkoutVehicle(token);
        }

        theFacility.printFacilityStatus(null);
    }
}
