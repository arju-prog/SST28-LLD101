import java.util.Map;

public class LegacyAddOnPricing implements AddOnPricing {
    private final Map<AddOn, Money> byAddOn = Map.of(
            AddOn.MESS, new Money(1000.0),
            AddOn.LAUNDRY, new Money(500.0),
            AddOn.GYM, new Money(300.0)
    );

    @Override
    public Money monthlyFeeFor(AddOn addOn) {
        Money m = byAddOn.get(addOn);
        return m != null ? m : new Money(0.0);
    }
}

