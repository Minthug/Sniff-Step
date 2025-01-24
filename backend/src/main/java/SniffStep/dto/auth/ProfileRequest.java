package SniffStep.dto.auth;

import SniffStep.entity.Member;

public record ProfileRequest(Long id, String nickname, String email, String phoneNumber, String profileImage) {

    public static ProfileRequest fromMember(Member member) {
        String imageUrl = member.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = "https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/default_profile.jpeg";
        }

        return new ProfileRequest(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getPhoneNumber(),
                imageUrl);
    }
}
