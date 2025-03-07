package backend.academy.bot.exception;

public class ScrapperException extends RuntimeException {
    public ScrapperException(String message) {
        super(message);
    }

    public ScrapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
