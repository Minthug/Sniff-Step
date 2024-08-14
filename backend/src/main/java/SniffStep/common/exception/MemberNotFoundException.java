package SniffStep.common.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
