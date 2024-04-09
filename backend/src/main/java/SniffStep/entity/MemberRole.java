package SniffStep.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum MemberRole {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MemberRole fromMemberRole(String value) {
        return Stream.of(MemberRole.values())
                .filter(memberRole -> StringUtils.equals(memberRole.getValue(), value))
                .findFirst()
                .orElse(null);
    }
}
