import java.util.LinkedList;
import java.util.Queue;

public class Elevator {

    private final int id;
    private int currentFloor;
    private ElevatorState state;
    private final double weightLimit;
    private double currentWeight;
    private final Door door;
    private final InternalPanel panel;
    private final Queue<Request> requestQueue;

    public Elevator(int id, double weightLimit) {
        this.id = id;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.weightLimit = weightLimit;
        this.currentWeight = 0;
        this.door = new Door();
        this.panel = new InternalPanel(this);
        this.requestQueue = new LinkedList<>();
    }

    public void addInternalRequest(int floor) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance. Request ignored.");
            return;
        }
        Direction dir = floor > currentFloor ? Direction.UP : Direction.DOWN;
        requestQueue.add(new Request(floor, dir));
        System.out.println("Elevator " + id + ": Internal request added for floor " + floor);
    }

    public void addExternalRequest(Request request) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance. Request ignored.");
            return;
        }
        requestQueue.add(request);
        System.out.println("Elevator " + id + ": External request added for floor " + request.getTargetFloor());
    }

    public void processNextRequest() {
        if (requestQueue.isEmpty()) {
            state = ElevatorState.IDLE;
            System.out.println("Elevator " + id + ": No pending requests. IDLE at floor " + currentFloor);
            return;
        }

        Request next = requestQueue.poll();
        moveToFloor(next.getTargetFloor());
    }

    public void moveToFloor(int targetFloor) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance. Cannot move.");
            return;
        }

        if (targetFloor == currentFloor) {
            System.out.println("Elevator " + id + " already at floor " + currentFloor);
            openDoor();
            return;
        }

        closeDoor();

        if (isOverweight()) {
            handleOverweight();
            return;
        }

        state = targetFloor > currentFloor ? ElevatorState.UP : ElevatorState.DOWN;
        System.out.println("Elevator " + id + ": Moving " + state + " from floor " + currentFloor + " to floor " + targetFloor);

        while (currentFloor != targetFloor) {
            currentFloor += (targetFloor > currentFloor) ? 1 : -1;
            System.out.println("Elevator " + id + ": ... passing floor " + currentFloor);
        }

        state = ElevatorState.IDLE;
        System.out.println("Elevator " + id + ": Arrived at floor " + currentFloor);
        openDoor();
    }

    public void updateWeight(double weight) {
        this.currentWeight = weight;
        System.out.println("Elevator " + id + ": Weight updated to " + currentWeight + " kg (limit: " + weightLimit + " kg)");
        if (isOverweight()) {
            handleOverweight();
        }
    }

    private boolean isOverweight() {
        return currentWeight > weightLimit;
    }

    private void handleOverweight() {
        System.out.println("*** Elevator " + id + ": OVERWEIGHT! Current: " + currentWeight + " kg, Limit: " + weightLimit + " kg ***");
        state = ElevatorState.IDLE;
        openDoor();
        System.out.println("*** Elevator " + id + ": Alarm ringing — please reduce weight ***");
    }

    public void openDoor() {
        door.open();
    }

    public void closeDoor() {
        door.close();
    }

    public void triggerEmergencyStop() {
        System.out.println("*** Elevator " + id + ": EMERGENCY STOP activated ***");
        state = ElevatorState.IDLE;
        openDoor();
        requestQueue.clear();
    }

    public void triggerAlarm() {
        System.out.println("*** Elevator " + id + ": ALARM ringing! Elevator stopped ***");
        state = ElevatorState.IDLE;
        openDoor();
    }

    public void setMaintenance(boolean maintenance) {
        if (maintenance) {
            state = ElevatorState.MAINTENANCE;
            requestQueue.clear();
            System.out.println("Elevator " + id + ": Entering MAINTENANCE mode.");
        } else {
            state = ElevatorState.IDLE;
            System.out.println("Elevator " + id + ": Exiting MAINTENANCE mode. Now IDLE.");
        }
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public InternalPanel getPanel() {
        return panel;
    }

    public boolean isAvailable() {
        return state != ElevatorState.MAINTENANCE;
    }
}
