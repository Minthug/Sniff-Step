package SniffStep.dto.member;

import SniffStep.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    private String email;

    public static MemberResponseDTO of(Member member) {
        return new MemberResponseDTO(member.getEmail());
    }
}
