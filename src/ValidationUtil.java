
public class ValidationUtil {
    // Validates if a name contains only letters and spaces
    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }

    // Validates if feedback comments are meaningful text
    public static boolean isValidFeedback(String feedback) {
        return feedback != null && feedback.matches("^[A-Za-z.,!? ]+$") && feedback.length() >= 3;
    }

    // Validates if a numeric rating is between 1 and 5
    public static boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }
}