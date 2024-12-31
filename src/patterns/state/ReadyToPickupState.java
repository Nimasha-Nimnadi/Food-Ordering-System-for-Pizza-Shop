package patterns.state;

import models.Order;

public class ReadyToPickupState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("The order is ready for pickup.");
    }

    @Override
    public void prev(Order order) {
        order.setState(new PreparationCompletedState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order is ready for pickup.");
    }

    @Override
    public String getStateName() {
        return "Ready to Pickup";
    }
}
