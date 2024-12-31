package patterns.observer;

import models.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Subject for managing observers and notifying them of order updates.
public class OrderUpdate {
    private final List<OrderObserver> observers = Collections.synchronizedList(new ArrayList<>());
    private Order order;

    public void addObserver(OrderObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null");
        }
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void setOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        this.order = order;
        notifyObservers();
    }


    private void notifyObservers() {
        synchronized (observers) {
            for (OrderObserver observer : observers) {
                observer.update(order);
            }
        }
    }
}
