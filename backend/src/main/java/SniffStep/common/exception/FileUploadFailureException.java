package SniffStep.common.exception;

public class FileUploadFailureException extends RuntimeException {
    public FileUploadFailureException(String message) {
        super(message);
    }

    public FileUploadFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
