package SniffStep.common.exception;

public class UnsupportedFileTypeException extends RuntimeException {

    public UnsupportedFileTypeException() {
    }

    public UnsupportedFileTypeException(String message) {
        super(message);
    }

    public UnsupportedFileTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
