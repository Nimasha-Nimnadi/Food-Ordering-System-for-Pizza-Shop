package patterns.strategy;

import java.util.ArrayList;
import java.util.List;

public class PromotionManager {
    private List<Promotion> activePromotions = new ArrayList<>();

    public void addPromotion(Promotion promotion) {
        activePromotions.add(promotion);
    }

    public double applyPromotions(String crust, String topping, double originalPrice) {
        double discountedPrice = originalPrice;
        for (Promotion promotion : activePromotions) {
            discountedPrice = promotion.applyPromotion(crust, topping, discountedPrice);
        }
        return discountedPrice;
    }
}