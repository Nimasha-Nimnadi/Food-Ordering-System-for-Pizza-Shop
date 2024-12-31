package patterns.strategy;
import java.time.LocalDate;
public class SeasonalDiscount implements Promotion {
    @Override
    public double applyPromotion(String crust, String topping, double originalPrice) {
        int currentMonth = LocalDate.now().getMonthValue(); // 12 for December
        if (currentMonth == 12 && "Thin Crust".equalsIgnoreCase(crust) && "Pepperoni".equalsIgnoreCase(topping)) {
            double discount = originalPrice * 0.10; // 10% discount
            return originalPrice - discount;
        }
        return originalPrice; // No discount
    }
}