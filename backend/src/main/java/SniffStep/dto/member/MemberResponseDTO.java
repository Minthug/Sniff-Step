package SniffStep.dto.member;

import SniffStep.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String email;
    private String nickname;
    private String introduce;
    private String phoneNumber;
    private String imageUrl;

    public static MemberResponseDTO of(Member member) {
        return new MemberResponseDTO(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getIntroduce(),
                member.getPhoneNumber(),
                member.getImageUrl()
        );
    }
}
