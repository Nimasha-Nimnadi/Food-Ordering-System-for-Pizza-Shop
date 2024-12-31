package models;

import patterns.observer.OrderUpdate;
import patterns.state.*;
import patterns.strategy.PaymentStrategy;
import patterns.strategy.PromotionManager;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int orderCounter = 1;
    private final String orderNumber; // Unique order number
    private List<Pizza> pizzas = new ArrayList<>();
    private String customerName;
    private String deliveryAddress;
    private OrderStatus status;
    private OrderState state;
    private double totalAmount;
    private PromotionManager promotionManager;
    private final OrderUpdate orderUpdate = new OrderUpdate();

    public Order(String customerName, String deliveryAddress) {
        this.orderNumber = String.format("Order%03d", orderCounter++);
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.promotionManager = promotionManager;
        this.status = OrderStatus.PLACED;
        this.state = new InPreparationState();
        this.totalAmount = 0.0;
    }
    public void addPizza(Pizza pizza, PromotionManager promotionManager) {
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza cannot be null");
        }
        pizzas.add(pizza);
        double discountedPrice = pizza.calculatePrice(promotionManager);
        totalAmount += discountedPrice;

        System.out.println("Discounted Price for this Pizza: RS" + discountedPrice);
    }




    public String getOrderNumber() {
        return orderNumber;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    //Process payment using the selected payment strategy
    public void processPayment(PaymentStrategy paymentStrategy) {
        if (paymentStrategy != null) {
            paymentStrategy.pay(totalAmount);
        } else {
            System.out.println("Payment method not selected. Payment failed.");
        }
    }
    //admin features
    public void nextState() {
        if (state != null) {
            state.next(this);
        }
    }
    public void previousState() {
        if (state != null) {
            state.prev(this);
        }
    }

    public void printCurrentState() {
        if (state != null) {
            state.printStatus();
        }
    }

    public void setPromotionManager(PromotionManager promotionManager) {
        this.promotionManager = promotionManager;
    }


    @Override
    public String toString() {
        return "Order Number: " + orderNumber + "\n" +
                "Customer Name: " + customerName + "\n" +
                "Delivery Address: " + (deliveryAddress.isEmpty() ? "Pickup" : deliveryAddress) + "\n" +
                "Status: " + status + "\n" +
                "Pizzas: " + pizzas + "\n" +
                "Total Amount: RS" + totalAmount + "\n" +
                "Order State: " + (state == null ? "Not Set" : state.getStateName());
    }
}
