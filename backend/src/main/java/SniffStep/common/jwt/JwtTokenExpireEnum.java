package SniffStep.common.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtTokenExpireEnum {

    ACCESS_TOKEN_EXPIRE_TIME(1000 * 60 * 30), // 30Min
    REFRESH_TOKEN_EXPIRE_TIME(1000 * 60 * 60 * 24 * 7), // 7Day
    REISSUE_EXPIRATION_TIME(1000 * 60 * 60 * 24 * 3); // 3Day

    private final long value;
}
