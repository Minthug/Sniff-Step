package SniffStep.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Stream;

public enum MemberRole implements GrantedAuthority {

    USER, ADMIN;


    @Override
    public String getAuthority() {
        return this.name();
    }
}
