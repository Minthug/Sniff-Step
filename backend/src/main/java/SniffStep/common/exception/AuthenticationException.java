package SniffStep.common.exception;

public class AuthenticationException extends RuntimeException {

        public AuthenticationException(String message) {
            super(message);
        }

        public static class FailAuthenticationException extends AuthenticationException {
            public FailAuthenticationException(String message) {
                super(message);
            }
        }
}
