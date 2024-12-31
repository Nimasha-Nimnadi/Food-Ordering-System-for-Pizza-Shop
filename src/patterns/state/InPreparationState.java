package patterns.state;

import models.Order;

public class InPreparationState implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new PreparationCompletedState());
    }

    @Override
    public void prev(Order order) {
        System.out.println("The order is in its initial state.");
    }

    @Override
    public void printStatus() {
        System.out.println("Order is being prepared.");
    }

    @Override
    public String getStateName() {
        return "In Preparation";
    }
}
