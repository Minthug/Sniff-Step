package SniffStep.common.exception;

public class DuplicateNicknameException extends RuntimeException {

        public DuplicateNicknameException(String message) {
            super(message);
        }

        public DuplicateNicknameException(String message, Throwable cause) {
            super(message, cause);
        }
}
