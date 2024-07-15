package SniffStep.dto.auth;

import SniffStep.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long id;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String profileImage;

    public static ProfileDTO fromMember(Member member) {
        String imageUrl = member.getImageUrl();
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = "https://sniff-step-s3-bucket.s3.ap-northeast-2.amazonaws.com/default_profile.jpeg";
        }

        return new ProfileDTO(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getPhoneNumber(),
                imageUrl
        );
    }
}
