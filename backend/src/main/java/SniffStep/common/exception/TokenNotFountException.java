package SniffStep.common.exception;

public class TokenNotFountException extends CustomUnauthorizedException {

    public TokenNotFountException(final String email) {
        super(String.format("토큰을 찾을 수 없습니다. - request info { email : %s }", email));
    }
}
