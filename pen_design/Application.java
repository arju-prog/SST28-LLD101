package pen;

public class Application {
    public static void main(String[] args) {
        WritingInstrument smoothGel = InstrumentBuilder.construct(InkType.GEL, "Navy Blue", CapType.PUSH_BUTTON);
        WritingInstrument premiumFountain = InstrumentBuilder.construct(InkType.FOUNTAIN, "Jet Black",
                CapType.REMOVABLE);

        try {
            smoothGel.draw();
        } catch (Exception ex) {
            System.out.println("Fault: " + ex.getMessage());
        }

        try {
            smoothGel.activate();
            smoothGel.draw();
            smoothGel.deactivate();

            System.out.println("===================");

            premiumFountain.activate();
            premiumFountain.draw();
            premiumFountain.restoreInk("Crimson Red");
            premiumFountain.draw();
            premiumFountain.deactivate();

        } catch (Exception ex) {
            System.out.println("Fault: " + ex.getMessage());
        }
    }
}
