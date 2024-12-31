package patterns.state;

import models.Order;

public class PlacedState implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new InPreparationState());
    }

    @Override
    public void prev(Order order) {
        System.out.println("The order is in its initial state.");
    }

    @Override
    public void printStatus() {
        System.out.println("Order placed.");
    }

    @Override
    public String getStateName() {
        return "Placed";
    }
}
