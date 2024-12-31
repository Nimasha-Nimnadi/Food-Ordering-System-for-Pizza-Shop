package models;

//This class epresents feedback provided by a customer, including comments and a rating.
public class Feedback {
    private final String customerName;
    private final String comments;
    private final int rating;

    public Feedback(String customerName, String comments, int rating) {
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (comments == null || comments.isEmpty()) {
            throw new IllegalArgumentException("Comments cannot be null or empty");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.customerName = customerName;
        this.comments = comments;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Feedback [customerName=" + customerName + ", comments=" + comments + ", rating=" + rating + "]";
    }
}
