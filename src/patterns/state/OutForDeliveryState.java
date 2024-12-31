package patterns.state;

import models.Order;

public class OutForDeliveryState implements OrderState {
    @Override
    public void next(Order order) {
        order.setState(new DeliveredState());
    }

    @Override
    public void prev(Order order) {
        order.setState(new PreparationCompletedState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order is out for delivery.");
    }

    @Override
    public String getStateName() {
        return "Out For Delivery";
    }
}
