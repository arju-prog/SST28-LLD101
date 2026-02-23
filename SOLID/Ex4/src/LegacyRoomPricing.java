import java.util.Map;

public class LegacyRoomPricing implements RoomPricing {
    private final Map<Integer, Money> byType = Map.of(
            LegacyRoomTypes.SINGLE, new Money(14000.0),
            LegacyRoomTypes.DOUBLE, new Money(15000.0),
            LegacyRoomTypes.TRIPLE, new Money(12000.0),
            LegacyRoomTypes.DELUXE, new Money(16000.0)
    );

    @Override
    public Money monthlyBaseFor(int roomType) {
        Money m = byType.get(roomType);
        if (m != null) return m;
        return byType.get(LegacyRoomTypes.DELUXE);
    }
}

