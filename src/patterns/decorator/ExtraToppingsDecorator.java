package patterns.decorator;

import models.Pizza;

//adding extra toppings to a pizza.
public class ExtraToppingsDecorator extends PizzaDecorator {
    public ExtraToppingsDecorator(Pizza decoratedPizza) {
        super(decoratedPizza);
    }

    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ", Extra Toppings";
    }
}
