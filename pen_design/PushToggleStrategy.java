package pen;

public class PushToggleStrategy implements ToggleStateStrategy {
    @Override
    public void engage() {
        System.out.println("Pressing the top mechanism to reveal the tip.");
    }

    @Override
    public void disengage() {
        System.out.println("Clicking again to hide the tip.");
    }
}
