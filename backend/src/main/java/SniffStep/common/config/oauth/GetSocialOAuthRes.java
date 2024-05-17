package SniffStep.common.config.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSocialOAuthRes {

    private String jwtToken;
    private Long userNum;
    private String accessToken;
    private String tokenType;
    private String email;
    private String name;
}
