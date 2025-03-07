package backend.academy.scrapper.controller;

import backend.academy.scrapper.exception.ApiError;
import backend.academy.scrapper.exception.LinkDontExistException;
import backend.academy.scrapper.exception.NotSuchSDKException;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BotExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiError> catchLinkDontExistException(LinkDontExistException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        int statusCode = status.value();
        ApiError apiError = new ApiError(
                status.getReasonPhrase(),
                Integer.toString(statusCode),
                LinkDontExistException.class.getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList());
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> catchNotSuchSDKException(NotSuchSDKException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        int statusCode = status.value();
        ApiError apiError = new ApiError(
                status.getReasonPhrase(),
                Integer.toString(statusCode),
                LinkDontExistException.class.getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList());
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> catchRuntimeException(RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        int statusCode = status.value();
        ApiError apiError = new ApiError(
                status.getReasonPhrase(),
                Integer.toString(statusCode),
                LinkDontExistException.class.getName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList());
        return new ResponseEntity<>(apiError, status);
    }
}
