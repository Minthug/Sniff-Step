package SniffStep.dto.member;

import SniffStep.entity.Member;
import SniffStep.entity.MemberType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private long id;
    private String email;
    private String nickname;
    private String introduce;
    private String phoneNumber;
    private String imageUrl;
    private MemberType memberType;

    public static MemberDTO toDto(Member member) {
        if (member.getMemberType() == MemberType.GOOGLE) {
            member.updateProfileImageUrl(member.getImageUrl());
        } else if (member.getImageUrl() == null || member.getImageUrl().isEmpty()) {
            String defaultImageUrl = "https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/default_profile.jpeg";
            member.updateProfileImageUrl(defaultImageUrl);
        }
        return new MemberDTO(member.getId(), member.getEmail(),
                member.getNickname(), member.getIntroduce(),
                member.getPhoneNumber(), member.getImageUrl(), member.getMemberType());
    }
}