package models;

import patterns.strategy.PromotionManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//This class represents a customizable pizza with crust, sauce, cheese, toppings, and a custom name.
public class Pizza {
    private final String name; // Custom name for the pizza
    private final String crust;
    private final String sauce;
    private final List<String> toppings;
    private final String cheese;
    private double basePrice;


    //use builder design pattern
    private Pizza(PizzaBuilder builder) {
        this.name = builder.name;
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.toppings = Collections.unmodifiableList(builder.toppings);
        this.cheese = builder.cheese;
        this.basePrice = 1200.0; // Base price for any pizza
    }

    public double calculatePrice(PromotionManager promotionManager) {
        double totalPrice = basePrice;

        if (toppings != null && !toppings.isEmpty()) {
            for (String topping : toppings) {
                totalPrice += 200.0;
            }
        }

        // Apply promotions (if any)
        if (promotionManager != null) {
            for (String topping : toppings) { // Check each topping
                totalPrice = promotionManager.applyPromotions(crust, topping, totalPrice);
            }
        }


        // Sauce price
        if (PizzaBuilder.saucePrices.containsKey(sauce)) {
            totalPrice += PizzaBuilder.saucePrices.get(sauce);
        }

        // Cheese price
        if (PizzaBuilder.cheesePrices.containsKey(cheese)) {
            totalPrice += PizzaBuilder.cheesePrices.get(cheese);
        }

        return totalPrice;
    }
    public String getDescription() {
        return (name == null || name.isEmpty() ? "Unnamed Pizza" : name) + ": Pizza with " + crust + " crust, " + sauce + " sauce, cheese: "
                + cheese + ", toppings: " + (toppings.isEmpty() ? "No Toppings" : String.join(", ", toppings));
    }

    @Override
    public String toString() {
        return getDescription();
    }

    //Builder class for constructing pizzas with various customizations.
    public static class PizzaBuilder {
        private String name;
        private String crust;
        private String sauce;
        private final List<String> toppings = new ArrayList<>();
        private String cheese;

        public static final java.util.Map<String, Double> saucePrices = new java.util.HashMap<>();
        public static final java.util.Map<String, Double> cheesePrices = new java.util.HashMap<>();

        static {
            // Populate prices for sauces
            saucePrices.put("Marinara", 300.0);
            saucePrices.put("Barbecue", 200.0);
            saucePrices.put("Pesto", 500.0);
            saucePrices.put("Alfredo", 300.0);

            // Populate prices for cheeses
            cheesePrices.put("Mozzarella", 500.0);
            cheesePrices.put("Cheddar", 200.0);
            cheesePrices.put("Parmesan", 400.0);
        }

        public PizzaBuilder setName(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Pizza name cannot be null or empty");
            }
            this.name = name;
            return this;
        }

        public PizzaBuilder setCrust(String crust) {
            if (crust == null || crust.isEmpty()) {
                throw new IllegalArgumentException("Crust cannot be null or empty");
            }
            this.crust = crust;
            return this;
        }

        public PizzaBuilder setSauce(String sauce) {
            if (sauce == null || sauce.isEmpty()) {
                throw new IllegalArgumentException("Sauce cannot be null or empty");
            }
            this.sauce = sauce;
            return this;
        }

        public PizzaBuilder addTopping(String topping) {
            if (topping == null || topping.isEmpty()) {
                throw new IllegalArgumentException("Topping cannot be null or empty");
            }
            if (topping.equalsIgnoreCase("No Toppings")) {
                System.out.println("No toppings will be added.");
                return this;
            }
            this.toppings.add(topping);
            return this;
        }

        public PizzaBuilder setCheese(String cheese) {
            if (cheese == null || cheese.isEmpty()) {
                throw new IllegalArgumentException("Cheese cannot be null or empty");
            }
            this.cheese = cheese;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }
}
