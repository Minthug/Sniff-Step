package SniffStep.common.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface SocialOAuth {

    String getOAuthRedirectURL(String type);

    GoogleOAuthToken requestAccessToken(String code, String type);

    GoogleOAuthToken getAccessToken(ResponseEntity<String> accessToken) throws JsonProcessingException;

    ResponseEntity<String> requestUserInfo(GoogleOAuthToken googleOAuthToken);

    GoogleUser getUserInfo(ResponseEntity<String> userInfo) throws JsonProcessingException;
}
