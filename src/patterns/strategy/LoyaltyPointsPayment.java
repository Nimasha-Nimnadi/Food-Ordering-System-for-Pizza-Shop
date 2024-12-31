package patterns.strategy;

import models.Customer;

public class LoyaltyPointsPayment implements PaymentStrategy {
    private Customer customer;

    public LoyaltyPointsPayment(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void pay(double amount) {
        int pointsToUse = (int) amount;
        if (customer.getLoyaltyPoints() >= pointsToUse) {
            customer.redeemPoints(pointsToUse);
            System.out.println("Paid RS" + amount + " using Loyalty Points. Remaining points: " + customer.getLoyaltyPoints());
        } else {
            System.out.println("Not enough loyalty points to complete the payment. Points required: " + pointsToUse);
        }
    }
}
