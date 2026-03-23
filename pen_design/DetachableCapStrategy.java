package pen;

public class DetachableCapStrategy implements ToggleStateStrategy {
    @Override
    public void engage() {
        System.out.println("Taking off the pen cover.");
    }

    @Override
    public void disengage() {
        System.out.println("Sealing the pen with its cover.");
    }
}
