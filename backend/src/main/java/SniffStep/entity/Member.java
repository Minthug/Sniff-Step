package SniffStep.entity;

import SniffStep.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String name;

    private String nickname;

    private String introduce;

    private String phoneNumber;
    private String password;

    private String imageUrl;
    private String socialId;
    private String accessToken;
    private String refreshToken;
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    public Member(String name, String email, String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public void hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateMember(String nickname, String introduce, String password) {
        this.nickname = nickname;
        this.introduce = introduce;
        this.password = password;
        onPreUpdate();
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member update(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    public void updateAccessToken(String updateAccessToken) {
        this.accessToken = updateAccessToken;
    }

    public void authorizeUser() {
        this.role = MemberRole.USER;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateOAuthInfo(String socialId, String provider) {
        this.socialId = socialId;
        this.provider = provider;
    }
}
