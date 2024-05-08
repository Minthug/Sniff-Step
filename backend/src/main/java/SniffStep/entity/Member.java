package SniffStep.entity;

import SniffStep.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String email;

    private String name;

    private String nickname;

    private String introduce;

    private String phoneNumber;
    private String password;
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Builder
    public Member(Long id, String loginId, String email, String name, String nickname, String introduce, String phoneNumber, String password, String provider, String providerId, MemberRole role, MemberType type) {
        this.id = id;
        this.loginId = loginId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.type = type;
    }

    public Member hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
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
}
