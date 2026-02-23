import java.util.*;

public class OrderPricingResult {
    public final List<InvoiceLineItem> items;
    public final double subtotal;

    public OrderPricingResult(List<InvoiceLineItem> items, double subtotal) {
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
        this.subtotal = subtotal;
    }
}

