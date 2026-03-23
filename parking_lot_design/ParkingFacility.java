package parking_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingFacility {
    private List<ParkingSpace> theSpaces;
    private List<AccessPoint> theGates;
    private SpotAllocationStrategy allocationLogic;
    private FeeCalculationStrategy feeLogic;

    public ParkingFacility(SpotAllocationStrategy alloc, FeeCalculationStrategy feeCalc) {
        this.theSpaces = new ArrayList<>();
        this.theGates = new ArrayList<>();
        this.allocationLogic = alloc;
        this.feeLogic = feeCalc;
    }

    public void registerSpace(ParkingSpace space) {
        theSpaces.add(space);
    }

    public void registerGate(AccessPoint gate) {
        theGates.add(gate);
    }

    public ParkingToken admitVehicle(Automobile car, AccessPoint entry) {
        try {
            System.out.println("Processing " + car.getCategory() + " at Entry " + entry.getIdentifier());
            ParkingSpace targetSpace = allocationLogic.locateBestSpot(entry, theSpaces, car.getCategory());

            targetSpace.markTaken();
            ParkingToken token = new ParkingToken(car, targetSpace);
            System.out
                    .println("Allocated Space: " + targetSpace.getIdentifier() + " on Level " + targetSpace.getLevel());
            return token;
        } catch (Exception err) {
            System.out.println("Alert: " + err.getMessage());
            return null;
        }
    }

    public double checkoutVehicle(ParkingToken token) {
        token.getAssignedSpace().markFree();
        long now = System.currentTimeMillis();
        double amount = feeLogic.computeCharge(token, now);

        System.out.println("Auto " + token.getAuto().getRegNumber() +
                " leaving. Space " + token.getAssignedSpace().getIdentifier() + " freed. Cost: $" + amount);
        return amount;
    }

    public void printFacilityStatus(SpaceSize specificSize) {
        Map<SpaceSize, Long> freeSpacesMap = theSpaces.stream()
                .filter(sp -> !sp.isTaken())
                .filter(sp -> specificSize == null || sp.getSizeCategory() == specificSize)
                .collect(Collectors.groupingBy(ParkingSpace::getSizeCategory, Collectors.counting()));

        System.out.println("\n*** Facility Status Report ***");
        if (freeSpacesMap.isEmpty()) {
            System.out.println("Zero spaces open.");
        } else {
            freeSpacesMap.forEach((cat, qty) -> System.out.println(cat + " spots empty: " + qty));
        }
        System.out.println("******************************\n");
    }
}
