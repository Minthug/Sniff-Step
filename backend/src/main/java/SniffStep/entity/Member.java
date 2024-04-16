package SniffStep.entity;

import SniffStep.common.BaseTime;
import SniffStep.dto.MemberUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    private String introduce;

    private String phoneNumber;

//    private String profileImage;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRole memberRole;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String name, String nickname, String introduce, String phoneNumber, String password, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.introduce = introduce;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.memberRole = memberRole;
    }

    public Member hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public Member updateMember(MemberUpdateDTO memberUpdateDTO) {
        this.nickname = memberUpdateDTO.getNickname();
        this.introduce = memberUpdateDTO.getIntroduce();
        this.password = memberUpdateDTO.getPassword();
        return this;
    }
}
