package patterns.strategy;

public class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid RS" + amount + " using Cash.");
    }
}
