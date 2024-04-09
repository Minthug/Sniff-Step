package SniffStep.entity;

import SniffStep.common.BaseTime;
import SniffStep.dto.MemberUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole memberRole;

    @Builder
    public Member(Long id, String email, String name, String nickname, String phoneNumber, String password, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.memberRole = memberRole;
    }



    public Member hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public Member updateMember(MemberUpdateDTO memberUpdateDTO, PasswordEncoder passwordEncoder) {
        this.nickname = memberUpdateDTO.getNickname();
        this.password = passwordEncoder.encode(memberUpdateDTO.getPassword());
        return this;
    }
}
