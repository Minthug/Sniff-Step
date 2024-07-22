package SniffStep.common.jwt.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
