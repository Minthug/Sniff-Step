package SniffStep.common.config.oauth;

import java.util.Map;

public class GoogleOAuthUserInfo extends OAuthUserInfo {

    public GoogleOAuthUserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
