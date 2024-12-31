package admin;

import models.Order;

public class AdminService {

    /**
     * Move an order to the next state.
     */
    public void moveToNextState(Order order) {
        System.out.println("Moving order " + order.getOrderNumber() + " to the next state.");
        order.nextState();
        System.out.println("Updated order state: " + order.getState().getStateName());
    }

    /**
     * Move an order to the previous state.
     */
    public void moveToPreviousState(Order order) {
        System.out.println("Moving order " + order.getOrderNumber() + " to the previous state.");
        order.previousState();
        System.out.println("Updated order state: " + order.getState().getStateName());
    }

    /**
     * Print the current state of an order.
     */
    public void printOrderState(Order order) {
        System.out.println("Order " + order.getOrderNumber() + " current state:");
        order.printCurrentState();
    }
}
