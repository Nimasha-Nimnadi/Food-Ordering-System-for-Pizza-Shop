package patterns.chain;

import models.Order;
//Abstract base class for handling different parts of order customization
public abstract class OrderHandler {
    protected OrderHandler nextHandler;

    public void setNextHandler(OrderHandler nextHandler) {
        this.nextHandler = nextHandler;
    }


    public abstract void handleRequest(Order order);
}
