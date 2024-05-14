package SniffStep.common.jwt.dto;

import SniffStep.common.config.oauth.dto.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public record TokenResponseDTO(String accessToken, String refreshToken) {

    public static TokenResponse of(final String accessToken, final String refreshToken) {
        return new TokenResponse(accessToken, refreshToken);
    }
}
