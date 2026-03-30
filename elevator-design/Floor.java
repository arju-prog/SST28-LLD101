public class Floor {

    private final int floorNumber;
    private boolean underMaintenance;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.underMaintenance = false;
    }

    public void pressUpButton(ElevatorController controller) {
        if (underMaintenance) {
            System.out.println("Floor " + floorNumber + " is under maintenance. Button disabled.");
            return;
        }
        System.out.println("Floor " + floorNumber + ": UP button pressed.");
        controller.handleExternalRequest(floorNumber, Direction.UP);
    }

    public void pressDownButton(ElevatorController controller) {
        if (underMaintenance) {
            System.out.println("Floor " + floorNumber + " is under maintenance. Button disabled.");
            return;
        }
        System.out.println("Floor " + floorNumber + ": DOWN button pressed.");
        controller.handleExternalRequest(floorNumber, Direction.DOWN);
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
        System.out.println("Floor " + floorNumber + " maintenance mode: " + (underMaintenance ? "ON" : "OFF"));
    }

    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
