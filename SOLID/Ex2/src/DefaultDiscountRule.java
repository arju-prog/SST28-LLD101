public class DefaultDiscountRule implements DiscountRule {
    @Override
    public double calculateDiscount(String customerType, double subtotal, int distinctLines) {
        // hard-coded policy (matches original DiscountRules behavior)
        if ("student".equalsIgnoreCase(customerType)) {
            if (subtotal >= 180.0) return 10.0;
            return 0.0;
        }
        if ("staff".equalsIgnoreCase(customerType)) {
            if (distinctLines >= 3) return 15.0;
            return 5.0;
        }
        return 0.0;
    }
}
