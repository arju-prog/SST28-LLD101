import java.util.*;

public class OrderPricer {
    public OrderPricingResult price(List<OrderLine> lines, MenuItemResolver menuResolver) {
        List<InvoiceLineItem> items = new ArrayList<>();
        double subtotal = 0.0;

        for (OrderLine l : lines) {
            MenuItem item = menuResolver.resolve(l.itemId);
            if (item == null) {
                throw new IllegalArgumentException("Unknown menu item: " + l.itemId);
            }
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
            items.add(new InvoiceLineItem(item.name, l.qty, lineTotal));
        }

        return new OrderPricingResult(items, subtotal);
    }
}

