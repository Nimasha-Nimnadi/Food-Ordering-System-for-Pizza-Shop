package patterns.decorator;

import models.Pizza;

//class for adding additional features to pizzas.
public abstract class PizzaDecorator {
    protected final Pizza decoratedPizza;

    public PizzaDecorator(Pizza decoratedPizza) {
        if (decoratedPizza == null) {
            throw new IllegalArgumentException("Decorated pizza cannot be null");
        }
        this.decoratedPizza = decoratedPizza;
    }

    public abstract String getDescription();
}
