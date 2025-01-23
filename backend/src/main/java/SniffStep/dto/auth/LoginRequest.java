package SniffStep.dto.auth;

import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

public record LoginRequest(@NotNull String email, @NotNull String password) {

    public Member toMember(PasswordEncoder encoder) {
        return Member.builder()
                .email(email)
                .password(password)
                .role(MemberRole.USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
