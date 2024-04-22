package SniffStep.dto;

import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {

    private Long memberId;
    private String email;
    private String nickname;
    private MemberRole memberRole;

    public static MemberResponseDTO of(Member member){
        return new MemberResponseDTO(member.getId(), member.getEmail(), member.getNickname(), member.getRole());
    }
}
