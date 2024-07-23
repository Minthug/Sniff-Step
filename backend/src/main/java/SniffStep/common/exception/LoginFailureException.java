package SniffStep.common.exception;

public class LoginFailureException extends RuntimeException {

        public LoginFailureException(String message) {
            super(message);
        }

        public LoginFailureException(String message, Throwable cause) {
            super(message, cause);
        }
}
