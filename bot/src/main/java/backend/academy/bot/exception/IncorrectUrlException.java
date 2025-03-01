package backend.academy.bot.exception;

public class IncorrectUrlException extends RuntimeException{
    public IncorrectUrlException(String message) {
        super(message);
    }
}
