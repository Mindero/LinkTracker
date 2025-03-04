package backend.academy.scrapper.exception;

public class NotSuchSDKException extends Exception {
    public NotSuchSDKException(String message) {
        super(message);
    }

    public NotSuchSDKException(String message, Throwable cause) {
        super(message, cause);
    }
}
