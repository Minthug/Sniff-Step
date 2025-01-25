package SniffStep.dto.member;

import SniffStep.entity.Member;
import SniffStep.entity.MemberType;

public record MemberDetailResponse(Long id,
                                   String email,
                                   String nickname,
                                   String introduce,
                                   String phoneNumber,
                                   String imageUrl,
                                   MemberType memberType) {

    public static MemberDetailResponse from(Member member) {
        String imageUrl = member.getImageUrl();

        if (member.getMemberType() == MemberType.GOOGLE) {
            member.updateProfileImageUrl(imageUrl);
        } else if (imageUrl == null || imageUrl.isEmpty()) {
            String defaultImageUrl = "https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/default_profile.jpeg";
            member.updateProfileImageUrl(defaultImageUrl);
        }
        return new MemberDetailResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getIntroduce(),
                member.getPhoneNumber(),
                member.getImageUrl(),
                member.getMemberType()
        );
    }

}

