package pen;

public class CartridgeReplenish implements ReplenishStrategy {
    @Override
    public void addInk() {
        System.out.println("Swapping out the old ink cartridge with a new one.");
    }
}
