package pen;

public class WritingInstrument {
    private String shade;
    private boolean isReady;
    private final ScribbleStrategy scribbleMechanism;
    private final ReplenishStrategy replenishMechanism;
    private final ToggleStateStrategy stateMechanism;

    public WritingInstrument(String shade, ScribbleStrategy scribbleMechanism,
            ReplenishStrategy replenishMechanism, ToggleStateStrategy stateMechanism) {
        this.shade = shade;
        this.scribbleMechanism = scribbleMechanism;
        this.replenishMechanism = replenishMechanism;
        this.stateMechanism = stateMechanism;
        this.isReady = false;
    }

    public void activate() {
        stateMechanism.engage();
        this.isReady = true;
    }

    public void deactivate() {
        stateMechanism.disengage();
        this.isReady = false;
    }

    public void draw() throws Exception {
        if (!isReady) {
            throw new Exception("Unable to draw. The instrument is closed! Activate it first.");
        }
        System.out.print("<" + shade.toUpperCase() + " INK> ");
        scribbleMechanism.scribble();
    }

    public void restoreInk(String alteredShade) {
        replenishMechanism.addInk();
        this.shade = alteredShade;
        System.out.println("Instrument now contains " + alteredShade + " color.");
    }
}
