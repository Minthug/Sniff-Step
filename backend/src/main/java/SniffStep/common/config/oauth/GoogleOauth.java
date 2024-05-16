package SniffStep.common.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleOauth implements SocialOAuth {

    @Value("${https://accounts.google.com/o/oauth2/v2/auth}")
    private String GOOGLE_SNS_LOGIN_URL;

    @Value("${security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_SNS_CLIENT_ID;

    @Value("${security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_SNS_CALLBACK_URL;

    @Value("${security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${security.oauth2.client.registration.google.scope}")
    private String GOOGLE_DATA_ACCESS_SCOPE;

    // Stirng 값을 객체로 바꾸는 Mapper
    private final ObjectMapper objectMapper;

    // Google API로 요청을 보내고 받을 객체입니다.
    private final RestTemplate restTemplate;


    @Override
    public String getOAuthRedirectURL() {
        Map<String, Object> params = new HashMap<>();

        params.put("scope", GOOGLE_DATA_ACCESS_SCOPE);
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL = GOOGLE_SNS_LOGIN_URL + "?" + parameterString;

        log.info("redirectURL : {}", redirectURL);
        return redirectURL;
    }

    @Override
    public ResponseEntity<String> requestAccessToken(String code, String type) {
        String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> stringResponseEntity =
                restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL, params, String.class);

        return stringResponseEntity;
    }

    @Override
    public GoogleOAuthToken getAccessToken(ResponseEntity<String> accessToken) throws JsonProcessingException {
        log.info("accessTokenBody: {} ", accessToken.getBody());
        return objectMapper.readValue(accessToken.getBody(), GoogleOAuthToken.class);
    }

    @Override
    public ResponseEntity<String> requestUserInfo(GoogleOAuthToken googleOAuthToken) {
        String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        headers.add("Authorization","Bearer " + googleOAuthToken.getAccessToken());
        ResponseEntity<String> exchange = restTemplate.exchange(GOOGLE_USER_INFO_URL, HttpMethod.GET, request, String.class);
        System.out.println(exchange.getBody());
        return exchange;
    }

    @Override
    public GoogleUser getUserInfo(ResponseEntity<String> userInfo) throws JsonProcessingException {

        GoogleUser googleUser = objectMapper.readValue(userInfo.getBody(), GoogleUser.class);
        return googleUser;
    }
}
