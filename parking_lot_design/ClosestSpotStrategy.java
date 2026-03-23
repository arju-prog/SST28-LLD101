package parking_system;

import java.util.List;

public class ClosestSpotStrategy implements SpotAllocationStrategy {

    private SpaceSize mapVehicleToSpace(AutoCategory category) {
        switch (category) {
            case MOTORCYCLE:
                return SpaceSize.COMPACT;
            case SEDAN:
                return SpaceSize.STANDARD;
            case TRUCK:
                return SpaceSize.OVERSIZED;
            default:
                throw new IllegalArgumentException("Unrecognized automobile category");
        }
    }

    @Override
    public ParkingSpace locateBestSpot(AccessPoint origin, List<ParkingSpace> availableSpaces,
            AutoCategory autoCategory) throws Exception {
        SpaceSize neededSize = mapVehicleToSpace(autoCategory);

        ParkingSpace chosenSpace = null;
        double shortestDistance = Double.MAX_VALUE;

        for (ParkingSpace space : availableSpaces) {
            if (!space.isTaken() && space.getSizeCategory() == neededSize) {
                double dist = space.computeDistanceTo(origin);
                if (dist < shortestDistance) {
                    shortestDistance = dist;
                    chosenSpace = space;
                }
            }
        }

        if (chosenSpace == null) {
            throw new Exception("Facility is full for category: " + neededSize);
        }
        return chosenSpace;
    }
}
