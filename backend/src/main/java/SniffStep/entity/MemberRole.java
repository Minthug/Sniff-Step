package SniffStep.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum MemberRole implements GrantedAuthority {

    USER("ROLE_MEMBER", "유저"),
    ADMIN("ROLE_ADMIN", "관리자"),
    SOCIAL("ROLE_SOCIAL", "소셜");

    private final String key;
    private final String title;


    @Override
    public String getAuthority() {
        return this.name();
    }
}
