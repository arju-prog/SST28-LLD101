package pen;

public class InstrumentBuilder {
    public static WritingInstrument construct(InkType kind, String shade, CapType switchType) {
        ScribbleStrategy scribbleSetup;
        ReplenishStrategy replenishSetup;
        ToggleStateStrategy stateSetup;

        switch (kind) {
            case BALLPOINT:
                scribbleSetup = new BallPointScribble();
                replenishSetup = new CartridgeReplenish();
                break;
            case GEL:
                scribbleSetup = new GelInkScribble();
                replenishSetup = new CartridgeReplenish();
                break;
            case FOUNTAIN:
                scribbleSetup = new FountainScribble();
                replenishSetup = new LiquidReplenish();
                break;
            default:
                throw new IllegalArgumentException("Unrecognized InkType specified");
        }

        switch (switchType) {
            case REMOVABLE:
                stateSetup = new DetachableCapStrategy();
                break;
            case PUSH_BUTTON:
                stateSetup = new PushToggleStrategy();
                break;
            default:
                throw new IllegalArgumentException("Unrecognized CapType specified");
        }

        return new WritingInstrument(shade, scribbleSetup, replenishSetup, stateSetup);
    }
}
