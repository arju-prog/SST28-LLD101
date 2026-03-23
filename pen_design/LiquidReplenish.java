package pen;

public class LiquidReplenish implements ReplenishStrategy {
    @Override
    public void addInk() {
        System.out.println("Sucking ink directly from a small reservoir bottle.");
    }
}
