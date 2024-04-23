package SniffStep.common.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponseDTO {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

//    @Builder
//    public TokenResponseDTO(String accessToken, String refreshToken) {
//        this.accessToken = accessToken;
//        this.refreshToken = refreshToken;
//    }
}
