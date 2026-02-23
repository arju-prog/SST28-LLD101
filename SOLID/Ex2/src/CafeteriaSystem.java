import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceStore store;
    private final OrderPricer pricer;
    private final TaxRule taxRule;
    private final DiscountRule discountRule;
    private final InvoiceFormatter formatter;
    private final MenuItemResolver menuResolver;
    private int invoiceSeq = 1000;

    public CafeteriaSystem() {
        this.store = new FileStore();
        this.pricer = new OrderPricer();
        this.taxRule = new DefaultTaxRule();
        this.discountRule = new DefaultDiscountRule();
        this.formatter = new PlainTextInvoiceFormatter();
        this.menuResolver = new MenuItemMapResolver(menu);
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    // Refactored: CafeteriaSystem now orchestrates only; no formatting, tax/discount logic, or persistence details.
    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        // Price the order (menu lookup + subtotal calculation)
        OrderPricingResult pricing = pricer.price(lines, menuResolver);

        // Calculate tax
        double taxPct = taxRule.getTaxPercent(customerType);
        double tax = pricing.subtotal * (taxPct / 100.0);

        // Calculate discount
        double discount = discountRule.calculateDiscount(customerType, pricing.subtotal, lines.size());

        // Calculate total
        double total = pricing.subtotal + tax - discount;

        // Build invoice
        Invoice invoice = new Invoice(
            invId,
            customerType,
            pricing.items,
            pricing.subtotal,
            taxPct,
            tax,
            discount,
            total
        );

        // Format invoice
        String printable = formatter.format(invoice);
        System.out.print(printable);

        // Persist invoice
        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
