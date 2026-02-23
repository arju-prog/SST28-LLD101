import java.util.List;

public interface DiscountRule {
    double calculateDiscount(String customerType, double subtotal, int distinctLines);
}
