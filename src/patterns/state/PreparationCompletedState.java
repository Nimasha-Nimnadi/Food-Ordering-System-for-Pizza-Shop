package patterns.state;

import models.Order;

public class PreparationCompletedState implements OrderState {
    @Override
    public void next(Order order) {
        if (order.getDeliveryAddress().isEmpty()) {
            order.setState(new ReadyToPickupState());
        } else {
            order.setState(new OutForDeliveryState());
        }
    }

    @Override
    public void prev(Order order) {
        order.setState(new InPreparationState());
    }

    @Override
    public void printStatus() {
        System.out.println("Preparation completed.");
    }

    @Override
    public String getStateName() {
        return "Preparation Completed";
    }
}
