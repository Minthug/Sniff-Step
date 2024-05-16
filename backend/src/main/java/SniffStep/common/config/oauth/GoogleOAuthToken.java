package SniffStep.common.config.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GoogleOAuthToken {
    private String accessToken;
    private int expiresIn;
    private String scope;
    private String tokenType;
    private String idToken;
}
