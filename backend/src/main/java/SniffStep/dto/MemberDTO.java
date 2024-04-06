package SniffStep.dto;

import SniffStep.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;

//    public MemberDTO() {
//    }

    public void assignMemberDetails(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.phoneNumber = member.getPhoneNumber();
    }

    public MemberDTO(Member member) {
        assignMemberDetails(member);
    }

//    @Builder
//    public MemberDTO(Long id, String email, String name, String nickname, String phoneNumber) {
//        this.id = id;
//        this.email = email;
//        this.name = name;
//        this.nickname = nickname;
//        this.phoneNumber = phoneNumber;
//    }
}
