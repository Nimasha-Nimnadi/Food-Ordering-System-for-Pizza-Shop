import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.*;
import patterns.chain.CrustHandler;
import patterns.chain.OrderHandler;
import patterns.chain.SauceHandler;
import patterns.command.*;
import patterns.observer.*;
import patterns.strategy.*;

public class Main {
    private static List<Order> allOrders = new ArrayList<>(); // Global list to track all orders

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Pizza Shop!");

        // Take customer details with validation for email
        String customerName;
        while (true) {
            System.out.print("Enter your name (or type 0 to exit): ");
            customerName = scanner.nextLine();
            if (customerName.equals("0")) exitSystem(scanner);
            if (ValidationUtil.isValidName(customerName)) {
                break;
            } else {
                System.out.println("Invalid name. Names cannot contain numbers or special characters. Please try again.");
            }
        }

        String email;
        while (true) {
            System.out.print("Enter your email (or type 0 to exit): ");
            email = scanner.nextLine();
            if (email.equals("0")) exitSystem(scanner);
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email address. Please try again.");
            }
        }

        Customer customer = new Customer(customerName, email);

        boolean running = true;
        while (running) {
            // Display menu options
            System.out.println("\nPizza Shop Menu:");
            System.out.println("1. Create a custom pizza");
            System.out.println("2. View favorite pizzas");
            System.out.println("3. Reorder favorite pizza");
            System.out.println("4. Track order by order number");
            System.out.println("5. Provide feedback");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(scanner);
            if (choice == 0 || choice == 6) {
                System.out.println("Thank you for using the Pizza Shop system. Goodbye!");
                break;
            }

            switch (choice) {
                case 1:
                    createCustomPizza(scanner, customer);
                    break;

                case 2:
                    viewFavoritePizzas(customer);
                    break;

                case 3:
                    reorderFavoritePizza(scanner, customer);
                    break;

                case 4:
                    trackOrderByNumber(scanner);
                    break;

                case 5:
                    provideFeedback(scanner, customer);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void createCustomPizza(Scanner scanner, Customer customer) {
        PromotionManager promotionManager = new PromotionManager();
        promotionManager.addPromotion(new SeasonalDiscount());
        Pizza.PizzaBuilder builder = new Pizza.PizzaBuilder();

        // Build the customization chain
        OrderHandler crustHandler = new CrustHandler();
        OrderHandler sauceHandler = new SauceHandler();
        crustHandler.setNextHandler(sauceHandler);



        // Choose crust
        String crust = chooseOption(scanner, "Choose crust (or type 0 to exit):",
                new String[]{"Thin Crust", "Thick Crust", "Stuffed Crust", "Cheese Burst"});
        if (crust.equals("0")) exitSystem(scanner);
        builder.setCrust(crust);
        System.out.println("You selected: " + crust);

        // Choose sauce
        String sauce = chooseOption(scanner, "Choose sauce (or type 0 to exit):",
                new String[]{"Marinara", "Barbecue", "Pesto", "Alfredo"});
        if (sauce.equals("0")) exitSystem(scanner);
        builder.setSauce(sauce);
        System.out.println("You selected: " + sauce);

        // Choose cheese
        String cheese = chooseOption(scanner, "Choose cheese (or type 0 to exit):",
                new String[]{"Mozzarella", "Cheddar", "Parmesan", "No Cheese"});
        if (cheese.equals("0")) exitSystem(scanner);
        builder.setCheese(cheese);
        System.out.println("You selected: " + cheese);

        // Add toppings
        String topping = chooseOption(scanner, "Choose toppings (or type 0 to exit, or 4 for no toppings):",
                new String[]{"Pepperoni", "Mushrooms", "Onions", "No Toppings"});
        if (topping.equals("0")) exitSystem(scanner);
        if (!topping.equals("No Toppings")) {
            builder.addTopping(topping);
            System.out.println("You chose topping: " + topping);
        } else {
            System.out.println("No toppings selected.");
        }

        // Clear the input buffer
        scanner.nextLine();

        // Name the pizza
        String pizzaName;
        while (true) {
            System.out.print("Enter a name for your custom pizza (or type 0 to exit): ");
            pizzaName = scanner.nextLine();
            if (pizzaName.equals("0")) exitSystem(scanner);
            if (!pizzaName.isEmpty()) {
                builder.setName(pizzaName);
                break;
            }
            System.out.println("Pizza name cannot be empty. Please try again.");
        }

        Pizza pizza = builder.build();
        System.out.println("Custom pizza created: " + pizza);

        System.out.print("Enter delivery address (or type 'Pickup' for pickup orders): ");
        String deliveryAddress = scanner.nextLine();

        Order order = new Order(customer.getName(),deliveryAddress);
        crustHandler.handleRequest(order);
        order.addPizza(pizza, promotionManager);
        System.out.println("Order total: RS" + order.getTotalAmount());

        // Ask to save the pizza as a favorite
        System.out.print("Would you like to save this pizza as a favorite? (y/n or type 0 to exit): ");
        String saveFavorite = scanner.nextLine();
        if (saveFavorite.equals("0")) exitSystem(scanner);
        if (saveFavorite.equalsIgnoreCase("y")) {
            customer.addFavoritePizza(pizza);
            System.out.println("Pizza saved as a favorite!");
        } else {
            System.out.println("Pizza not saved as a favorite.");
        }

        // Confirm order placement
        System.out.print("Would you like to place this order? (y/n or type 0 to exit): ");
        String confirmOrder = scanner.nextLine();
        if (confirmOrder.equals("0")) exitSystem(scanner);
        if (confirmOrder.equalsIgnoreCase("y")) {
            Command placeOrderCommand = new PlaceOrderCommand(order);
            placeOrderCommand.execute();

            System.out.println("Order total: RS" + order.getTotalAmount());

            // Choose a payment method
            System.out.println("Choose a payment method:");
            System.out.println("1. Credit Card");
            System.out.println("2. Digital Wallet");
            System.out.println("3. Cash");
            System.out.print("Enter your choice (1-3): ");
            int paymentChoice = getIntInput(scanner);

            PaymentStrategy paymentStrategy;
            switch (paymentChoice) {
                case 1:
                    paymentStrategy = new CreditCardPayment();
                    break;
                case 2:
                    paymentStrategy = new DigitalWalletPayment();
                    break;
                case 3:
                    paymentStrategy = new CashPayment();
                    break;
                default:
                    System.out.println("Invalid payment method. Order cancelled.");
                    return;
            }

            // Process payment and add loyalty points
            order.processPayment(paymentStrategy);
            int loyaltyPoints = (int) (order.getTotalAmount() / 1000); // 1 point for every RS1000
            customer.earnPoints(loyaltyPoints);

            allOrders.add(order); // Add order to global list
            System.out.println("Order placed successfully!");
            System.out.println("Order Number: " + order.getOrderNumber());
        } else {
            System.out.println("Order not placed. Returning to main menu.");
        }
    }

    private static void viewFavoritePizzas(Customer customer) {
        System.out.println("Your favorite pizzas:");
        if (customer.getFavoritePizzas().isEmpty()) {
            System.out.println("No favorite pizzas saved.");
        } else {
            int index = 1;
            for (Pizza favorite : customer.getFavoritePizzas()) {
                System.out.println(index + ". " + favorite);
                index++;
            }
        }
    }

    private static void reorderFavoritePizza(Scanner scanner, Customer customer) {
        if (customer.getFavoritePizzas().isEmpty()) {
            System.out.println("No favorite pizzas saved.");
            return;
        }

        // Display favorite pizzas
        System.out.println("Your favorite pizzas:");
        int index = 1;
        for (Pizza favorite : customer.getFavoritePizzas()) {
            System.out.println(index + ". " + favorite);
            index++;
        }

        // Prompt the user to select a favorite pizza
        System.out.print("Enter the number of the pizza you want to reorder (or type 0 to exit): ");
        int choice = getIntInput(scanner);
        if (choice == 0) return;

        if (choice < 1 || choice > customer.getFavoritePizzas().size()) {
            System.out.println("Invalid choice. Returning to main menu.");
            return;
        }

        Pizza selectedPizza = customer.getFavoritePizzas().get(choice - 1);

        // Create a PromotionManager and add promotions
        PromotionManager promotionManager = new PromotionManager();
        promotionManager.addPromotion(new SeasonalDiscount()); // Add seasonal promotions

        // Create an order
        Order order = new Order(customer.getName(), "Pickup");
        order.setPromotionManager(promotionManager); // Set PromotionManager separately
        order.addPizza(selectedPizza, promotionManager);

        System.out.println("Order total after promotions: RS" + order.getTotalAmount());

        // Select payment method
        System.out.println("Choose a payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Digital Wallet");
        System.out.println("3. Cash");
        System.out.print("Enter your choice (1-3): ");
        int paymentChoice = getIntInput(scanner);

        PaymentStrategy paymentStrategy;
        switch (paymentChoice) {
            case 1:
                paymentStrategy = new CreditCardPayment();
                break;
            case 2:
                paymentStrategy = new DigitalWalletPayment();
                break;
            case 3:
                paymentStrategy = new CashPayment();
                break;
            default:
                System.out.println("Invalid payment method. Order cancelled.");
                return;
        }

        // Process payment
        order.processPayment(paymentStrategy);

        // Add loyalty points
        int loyaltyPoints = (int) (order.getTotalAmount() / 1000); // 1 point for every 1000 spent
        customer.earnPoints(loyaltyPoints);

        // Add the order to the global order list
        allOrders.add(order);

        System.out.println("Reordered successfully!");
        System.out.println("Order Number: " + order.getOrderNumber());
    }


    private static void trackOrderByNumber(Scanner scanner) {
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter the order number to track (or type 0 to exit): ");
        String orderNumber = scanner.nextLine();
        if (orderNumber.equals("0")) {
            System.out.println("Returning to main menu...");
            return;
        }

        // Find the order by number
        Order order = findOrderByNumber(orderNumber);
        if (order == null) {
            System.out.println("No order found with number: " + orderNumber);
        } else {
            System.out.println("Order Details:\n" + order);
        }
    }

    private static void provideFeedback(Scanner scanner, Customer customer) {
        scanner.nextLine(); // Clear leftover input buffer
        System.out.print("Enter the order number to provide feedback (or type 0 to exit): ");
        String orderNumber = scanner.nextLine();
        if (orderNumber.equals("0")) return;

        // Find the order by number
        Order order = findOrderByNumber(orderNumber);
        if (order == null) {
            System.out.println("No order found with number: " + orderNumber);
            return;
        }

        // Get feedback for the order
        String comments;
        while (true) {
            System.out.print("Enter your comments (or type 0 to exit): ");
            comments = scanner.nextLine();
            if (comments.equals("0")) return;
            if (ValidationUtil.isValidFeedback(comments)) {
                break;
            } else {
                System.out.println("Invalid feedback. Comments must contain letters and meaningful text. Please try again.");
            }
        }
        int rating;
        while (true) {
            System.out.print("Rate the order (1-5 or type 0 to exit): ");
            rating = getIntInput(scanner);
            if (rating == 0) return;
            if (ValidationUtil.isValidRating(rating)) {
                break;
            } else {
                System.out.println("Invalid rating. Please enter a number between 1 and 5.");
            }
        }

        // Create Feedback object and execute the command
        Feedback feedback = new Feedback(customer.getName(), comments, rating);
        Command provideFeedbackCommand = new ProvideFeedbackCommand(feedback);
        provideFeedbackCommand.execute();

        System.out.println("Thank you for your feedback!");
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private static int getIntInput(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1; // Invalid input
        }
    }


    private static String chooseOption(Scanner scanner, String prompt, String[] options) {
        while (true) {
            System.out.println(prompt);
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.print("Enter your choice (1-" + options.length + " or 0 to exit): ");
            int choice = getIntInput(scanner);
            if (choice == 0) return "0"; // Exit option
            if (choice > 0 && choice <= options.length) {
                return options[choice - 1];
            }
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static Order findOrderByNumber(String orderNumber) {
        for (Order order : allOrders) {
            if (order.getOrderNumber().equalsIgnoreCase(orderNumber)) {
                return order;
            }
        }
        return null;
    }

    private static void exitSystem(Scanner scanner) {
        System.out.println("Exiting the Pizza Shop system. Goodbye!");
        scanner.close();
        System.exit(0);
    }
}