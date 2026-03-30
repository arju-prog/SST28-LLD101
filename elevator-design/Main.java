import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // --- Setup: 2 elevators with different weight limits, 5 floors ---
        Elevator elevatorA = new Elevator(1, 700);   // 700 kg limit
        Elevator elevatorB = new Elevator(2, 500);   // 500 kg limit

        List<Elevator> elevators = Arrays.asList(elevatorA, elevatorB);
        ElevatorController controller = new ElevatorController(elevators);

        Floor floor0 = new Floor(0);
        Floor floor1 = new Floor(1);
        Floor floor2 = new Floor(2);
        Floor floor3 = new Floor(3);
        Floor floor4 = new Floor(4);

        // === Scenario 1: External button press (UP from floor 2) ===
        System.out.println("========== Scenario 1: External UP request from Floor 2 ==========");
        floor2.pressUpButton(controller);

        // === Scenario 2: Internal panel — passenger selects floor 4 ===
        System.out.println("\n========== Scenario 2: Internal panel — go to Floor 4 ==========");
        elevatorA.getPanel().selectFloor(4);
        elevatorA.processNextRequest();

        // === Scenario 3: Open / Close door from inside ===
        System.out.println("\n========== Scenario 3: Open and Close door ==========");
        elevatorA.getPanel().pressOpen();
        elevatorA.getPanel().pressClose();

        // === Scenario 4: Overweight scenario ===
        System.out.println("\n========== Scenario 4: Overweight ==========");
        elevatorB.updateWeight(600);  // exceeds 500 kg limit

        // === Scenario 5: Alarm button ===
        System.out.println("\n========== Scenario 5: Alarm pressed ==========");
        elevatorB.getPanel().pressAlarm();

        // === Scenario 6: Emergency stop ===
        System.out.println("\n========== Scenario 6: Emergency stop ==========");
        elevatorA.getPanel().selectFloor(0);
        elevatorA.getPanel().pressEmergency();

        // === Scenario 7: Maintenance mode on elevator ===
        System.out.println("\n========== Scenario 7: Elevator maintenance ==========");
        elevatorB.setMaintenance(true);
        floor1.pressUpButton(controller);  // only elevator A should be dispatched

        // === Scenario 8: Floor under maintenance ===
        System.out.println("\n========== Scenario 8: Floor under maintenance ==========");
        floor3.setUnderMaintenance(true);
        floor3.pressUpButton(controller);  // should be disabled

        // === Scenario 9: Restore from maintenance ===
        System.out.println("\n========== Scenario 9: Restore elevator and floor ==========");
        elevatorB.setMaintenance(false);
        floor3.setUnderMaintenance(false);
        floor3.pressDownButton(controller);  // should work now
    }
}
