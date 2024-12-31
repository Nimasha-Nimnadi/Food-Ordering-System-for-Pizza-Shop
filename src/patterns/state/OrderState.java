package patterns.state;

import models.Order;

public interface OrderState {
    void next(Order order); // Transition to the next state
    void prev(Order order); // Transition to the previous state
    void printStatus(); // Print the current state
    String getStateName(); // Get the name of the current state
}
