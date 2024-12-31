package patterns.strategy;

public interface Promotion {
    double applyPromotion(String crust, String topping, double originalPrice);
}