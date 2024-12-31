package patterns.command;

import models.Order;
import models.OrderStatus;

//Command for placing an order and updating its status.
public class PlaceOrderCommand implements Command {
    private final Order order;

    public PlaceOrderCommand(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        this.order = order;
    }

    @Override
    public void execute() {
        order.setStatus(OrderStatus.IN_PREPARATION);
        System.out.println("Order placed and is now in preparation: " + order);
    }
}
