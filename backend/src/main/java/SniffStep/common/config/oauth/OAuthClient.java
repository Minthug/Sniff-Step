package SniffStep.common.config.oauth;

import SniffStep.common.config.oauth.dto.GoogleTokenResponse;

public interface OAuthClient {

    GoogleTokenResponse getGoogleAccessToken(final String code);
}
