package SniffStep.common.config.oauth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class GoogleOAuthProperties {

    @Value("${security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.registration.google.end-point}")
    private String endPoint;

    @Value("${security.oauth2.client.registration.google.authorization-grant-type}")
    private String responseType;

    @Value("${security.oauth2.client.registration.google.scope}")
    private List<String> scopes;

    @Value("${security.oauth2.client.registration.google.access-type}")
    private String accessType;

    @Value("${security.oauth2.client.registration.google.token-uri}")
    private String tokenUri;

    @Value("${security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
}
