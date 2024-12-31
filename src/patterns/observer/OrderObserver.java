package patterns.observer;

import models.Order;

//Observer interface for receiving updates on order status changes.
public interface OrderObserver {
    void update(Order order);
}
