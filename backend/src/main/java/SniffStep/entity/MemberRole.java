package SniffStep.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    GUEST("ROLE_GUEST"),
    USER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN"),
    SOCIAL("ROLE_SOCIAL");

    private final String key;

}
