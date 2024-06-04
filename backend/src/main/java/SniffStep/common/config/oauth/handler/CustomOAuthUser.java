package SniffStep.common.config.oauth.handler;

import SniffStep.entity.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuthUser extends DefaultOAuth2User {

    private String email;
    private MemberRole role;

    public CustomOAuthUser(Collection<? extends GrantedAuthority> authorities,
                           Map<String, Object> attributes, String nameAttributeKey,
                           String email, MemberRole role) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
    }
}
