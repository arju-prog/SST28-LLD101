public class Projector implements Powerable, InputConnectable {
    private boolean on;

    @Override public void powerOn() { on = true; }
    @Override public void powerOff() { System.out.println("Projector OFF"); on = false; }
    @Override public void connectInput(String port) { if (on) System.out.println("Projector ON (" + port + ")"); }
}
