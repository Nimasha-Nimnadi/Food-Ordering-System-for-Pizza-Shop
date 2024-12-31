package patterns.chain;

import models.Order;

//Handles sauce customization in the order.
public class SauceHandler extends OrderHandler {
    @Override
    public void handleRequest(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (nextHandler != null) {
            nextHandler.handleRequest(order);
        }
    }
}
