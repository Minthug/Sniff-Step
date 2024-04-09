package SniffStep.dto;

import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private MemberRole memberRole;


    public MemberDTO(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.phoneNumber = member.getPhoneNumber();
        this.memberRole = member.getMemberRole();
    }

    @Builder
    public MemberDTO(Long id, String email, String name, String nickname, String phoneNumber, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.memberRole = memberRole;
    }
}
