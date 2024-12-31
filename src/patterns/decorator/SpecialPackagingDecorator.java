package patterns.decorator;

import models.Pizza;

//adding special packaging to a pizza.
public class SpecialPackagingDecorator extends PizzaDecorator {
    public SpecialPackagingDecorator(Pizza decoratedPizza) {
        super(decoratedPizza);
    }

    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ", Special Packaging";
    }
}
