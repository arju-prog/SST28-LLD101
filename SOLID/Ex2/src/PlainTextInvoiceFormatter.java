import java.util.List;

public class PlainTextInvoiceFormatter implements InvoiceFormatter {
    @Override
    public String format(Invoice invoice) {
        StringBuilder out = new StringBuilder();
        out.append("Invoice# ").append(invoice.id).append("\n");
        
        for (InvoiceLineItem item : invoice.items) {
            out.append(String.format("- %s x%d = %.2f\n", item.name, item.qty, item.lineTotal));
        }
        
        out.append(String.format("Subtotal: %.2f\n", invoice.subtotal));
        out.append(String.format("Tax(%.0f%%): %.2f\n", invoice.taxPercent, invoice.taxAmount));
        out.append(String.format("Discount: -%.2f\n", invoice.discountAmount));
        out.append(String.format("TOTAL: %.2f\n", invoice.total));
        
        return out.toString();
    }
}
