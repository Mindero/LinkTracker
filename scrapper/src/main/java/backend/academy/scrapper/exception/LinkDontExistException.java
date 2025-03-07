package backend.academy.scrapper.exception;

public class LinkDontExistException extends RuntimeException {
    public LinkDontExistException(String message) {
        super(message);
    }
}
