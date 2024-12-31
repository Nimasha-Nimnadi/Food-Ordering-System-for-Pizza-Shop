package models;

// This class manages the loyalty program for customers, allowing them to earn and redeem points.
public class LoyaltyProgram {
    private int points;
    public void addPoints(int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Points must be positive");
        }
        this.points += points;
    }
    public int getPoints() {
        return points;
    }
    public void redeemPoints(int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Points to redeem must be positive");
        }
        if (this.points >= points) {
            this.points -= points;
            System.out.println("Redeemed " + points + " points.");
        } else {
            System.out.println("Not enough points to redeem.");
        }
    }

    @Override
    public String toString() {
        return "LoyaltyProgram [points=" + points + "]";
    }
}
