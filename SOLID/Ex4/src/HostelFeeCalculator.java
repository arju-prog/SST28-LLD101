import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final RoomPricing roomPricing;
    private final AddOnPricing addOnPricing;

    public HostelFeeCalculator(FakeBookingRepo repo) {
        this(repo, new LegacyRoomPricing(), new LegacyAddOnPricing());
    }

    public HostelFeeCalculator(FakeBookingRepo repo, RoomPricing roomPricing, AddOnPricing addOnPricing) {
        this.repo = repo;
        this.roomPricing = roomPricing;
        this.addOnPricing = addOnPricing;
    }

    // OCP violation: switch + add-on branching + printing + persistence.
    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); // deterministic-ish
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        Money monthly = roomPricing.monthlyBaseFor(req.roomType);
        for (AddOn addOn : req.addOns) {
            monthly = monthly.plus(addOnPricing.monthlyFeeFor(addOn));
        }
        return monthly;
    }
}
