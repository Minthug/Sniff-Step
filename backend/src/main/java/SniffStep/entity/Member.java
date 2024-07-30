package SniffStep.entity;

import SniffStep.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;

    private String name;

    private String nickname;

    private String introduce;

    private String phoneNumber;
    private String password;

    @Column(name = "image_url")
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

    public void updateMember(String nickname, String introduce, String password, String phoneNumber, String imageUrl) {
        if (nickname != null && !nickname.isEmpty()) this.nickname = nickname;
        if (introduce != null) this.introduce = introduce;
        if (password != null && !password.isEmpty()) this.password = password;
        if (phoneNumber != null && !phoneNumber.isEmpty()) this.phoneNumber = phoneNumber;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            this.imageUrl = imageUrl;
        }
        onPreUpdate();
        log.info("Member updated: {}", this.imageUrl);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
    public void updateAccessToken(String updateAccessToken) {
        this.accessToken = updateAccessToken;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateOAuthInfo(String socialId, String provider) {
        this.socialId = socialId;
        this.provider = provider;
    }

    public void updateMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void updateProfileImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
