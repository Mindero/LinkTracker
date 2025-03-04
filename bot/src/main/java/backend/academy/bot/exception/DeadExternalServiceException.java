package backend.academy.bot.exception;

public class DeadExternalServiceException extends RuntimeException {
    public DeadExternalServiceException(String message) {
        super(message);
    }
}
