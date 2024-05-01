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

    private String email;

    private String name;

    private String nickname;

    private String introduce;

    private String phoneNumber;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberType type;


    @Builder
    public Member(Long id, String email, String name, String nickname, String introduce, String phoneNumber, String password, MemberRole role, MemberType type) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.phoneNumber = phoneNumber;
        this.password = password;
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
}
