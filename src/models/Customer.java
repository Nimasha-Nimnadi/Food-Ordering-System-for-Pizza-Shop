package models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private String email;
    private int loyaltyPoints;
    private List<Pizza> favoritePizzas;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.loyaltyPoints = 0;
        this.favoritePizzas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public List<Pizza> getFavoritePizzas() {
        return favoritePizzas;
    }

    public void earnPoints(int points) {
        loyaltyPoints += points;
        System.out.println(points + " Wow Your loyalty points added. Total points count : " + loyaltyPoints);
    }

    public void redeemPoints(int points) {
        if (loyaltyPoints >= points) {
            loyaltyPoints -= points;
            System.out.println("Redeemed " + points + " loyalty points. Remaining points: " + loyaltyPoints);
        } else {
            System.out.println("Not enough loyalty points to redeem. Required: " + points + ", Available: " + loyaltyPoints);
        }
    }

    public void addFavoritePizza(Pizza pizza) {
        favoritePizzas.add(pizza);
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", email=" + email + ", loyaltyPoints=" + loyaltyPoints + ", favoritePizzas=" + favoritePizzas + "]";
    }
}
