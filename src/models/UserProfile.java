package models;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private String name;
    private String email;
    private int loyaltyPoints;
    private List<Pizza> favoritePizzas;

    public UserProfile(String name, String email) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.name = name;
        this.email = email;
        this.loyaltyPoints = 0;
        this.favoritePizzas = new ArrayList<>();
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for loyalty points
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Add loyalty points
    public void earnPoints(int points) {
        if (points > 0) {
            this.loyaltyPoints += points;
        }
    }

    // Get favorite pizzas
    public List<Pizza> getFavoritePizzas() {
        return favoritePizzas;
    }

    // Add a favorite pizza
    public void addFavoritePizza(Pizza pizza) {
        if (pizza != null) {
            favoritePizzas.add(pizza);
        }
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", favoritePizzas=" + favoritePizzas +
                '}';
    }
}
