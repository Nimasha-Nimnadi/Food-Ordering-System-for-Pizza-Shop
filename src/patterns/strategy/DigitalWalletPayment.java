package patterns.strategy;

public class DigitalWalletPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid RS" + amount + " using Digital Wallet.");
    }
}
