package SniffStep.common.config.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSocialOAuthRes {

    private Long userNum;
    private String accessToken;
    private String refreshToken;
    private String email;
    private String name;
}
