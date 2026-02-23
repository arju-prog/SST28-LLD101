import java.util.*;

public class Invoice {
    public final String id;
    public final String customerType;
    public final List<InvoiceLineItem> items;

    public final double subtotal;
    public final double taxPercent;
    public final double taxAmount;
    public final double discountAmount;
    public final double total;

    public Invoice(
            String id,
            String customerType,
            List<InvoiceLineItem> items,
            double subtotal,
            double taxPercent,
            double taxAmount,
            double discountAmount,
            double total
    ) {
        this.id = id;
        this.customerType = customerType;
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
        this.subtotal = subtotal;
        this.taxPercent = taxPercent;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.total = total;
    }
}

