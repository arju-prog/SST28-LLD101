public class InternalPanel {

    private final Elevator elevator;

    public InternalPanel(Elevator elevator) {
        this.elevator = elevator;
    }

    public void selectFloor(int floor) {
        System.out.println("Elevator " + elevator.getId() + " panel: Floor " + floor + " selected.");
        elevator.addInternalRequest(floor);
    }

    public void pressOpen() {
        System.out.println("Elevator " + elevator.getId() + " panel: OPEN pressed.");
        elevator.openDoor();
    }

    public void pressClose() {
        System.out.println("Elevator " + elevator.getId() + " panel: CLOSE pressed.");
        elevator.closeDoor();
    }

    public void pressEmergency() {
        System.out.println("Elevator " + elevator.getId() + " panel: EMERGENCY pressed.");
        elevator.triggerEmergencyStop();
    }

    public void pressAlarm() {
        System.out.println("Elevator " + elevator.getId() + " panel: ALARM pressed.");
        elevator.triggerAlarm();
    }
}
