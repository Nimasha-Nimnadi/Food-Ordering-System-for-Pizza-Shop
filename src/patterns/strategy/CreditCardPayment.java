package patterns.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid RS" + amount + " using Credit Card.");
    }
}
