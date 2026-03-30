import java.util.List;

public class ElevatorController {

    private final List<Elevator> elevators;

    public ElevatorController(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void handleExternalRequest(int floor, Direction direction) {
        Elevator best = findBestElevator(floor, direction);
        if (best == null) {
            System.out.println("Controller: No available elevator for floor " + floor + " " + direction);
            return;
        }
        System.out.println("Controller: Dispatching Elevator " + best.getId() + " to floor " + floor);
        best.addExternalRequest(new Request(floor, direction));
        best.processNextRequest();
    }

    private Elevator findBestElevator(int floor, Direction direction) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (!elevator.isAvailable()) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - floor);

            // Prefer IDLE elevators, then same-direction elevators
            if (elevator.getState() == ElevatorState.IDLE) {
                if (distance < minDistance) {
                    minDistance = distance;
                    best = elevator;
                }
            } else if (isMovingToward(elevator, floor, direction)) {
                if (distance < minDistance) {
                    minDistance = distance;
                    best = elevator;
                }
            }
        }

        // Fallback: pick any available elevator closest
        if (best == null) {
            for (Elevator elevator : elevators) {
                if (!elevator.isAvailable()) continue;
                int distance = Math.abs(elevator.getCurrentFloor() - floor);
                if (distance < minDistance) {
                    minDistance = distance;
                    best = elevator;
                }
            }
        }

        return best;
    }

    private boolean isMovingToward(Elevator elevator, int floor, Direction direction) {
        if (elevator.getState() == ElevatorState.UP && direction == Direction.UP
                && elevator.getCurrentFloor() <= floor) {
            return true;
        }
        if (elevator.getState() == ElevatorState.DOWN && direction == Direction.DOWN
                && elevator.getCurrentFloor() >= floor) {
            return true;
        }
        return false;
    }
}
