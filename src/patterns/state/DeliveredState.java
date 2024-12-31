package patterns.state;

import models.Order;

public class DeliveredState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("The order has already been delivered.");
    }

    @Override
    public void prev(Order order) {
        order.setState(new OutForDeliveryState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order delivered.");
    }

    @Override
    public String getStateName() {
        return "Delivered";
    }
}
