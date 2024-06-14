package SniffStep.dto.member;

import SniffStep.entity.Member;
import SniffStep.entity.MemberType;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private long id;
    private String email;
    private String name;
    private String nickname;
    private String introduce;
    private String phoneNumber;
    private String imageUrl;
    private MemberType memberType;



    public static MemberDTO toDto(Member member) {
        return new MemberDTO(member.getId(), member.getEmail(), member.getName(),
                member.getNickname(), member.getIntroduce(),
                member.getPhoneNumber(), member.getImageUrl(), member.getMemberType());
    }
}