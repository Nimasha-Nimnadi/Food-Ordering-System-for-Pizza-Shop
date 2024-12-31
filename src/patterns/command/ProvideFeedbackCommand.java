package patterns.command;

import models.Feedback;

//Command for providing feedback for an order.
public class ProvideFeedbackCommand implements Command {
    private final Feedback feedback;

    public ProvideFeedbackCommand(Feedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback cannot be null");
        }
        this.feedback = feedback;
    }

    @Override
    public void execute() {
        System.out.println("Feedback received: " + feedback);
    }
}
