package SniffStep.dto.member;

import SniffStep.entity.Member;

public record MemberResponse(Long id, String email, String nickname, String introduce, String phoneNumber, String imageUrl) {

    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getIntroduce(),
                member.getPhoneNumber(),
                member.getImageUrl()
        );
    }
}

