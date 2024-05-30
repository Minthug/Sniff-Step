package SniffStep.common.config.oauth;

import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.entity.MemberType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuthUserInfo oAuthUserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuthUserInfo oAuthUserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuthUserInfo = oAuthUserInfo;
    }

    public static OAuthAttributes of(MemberType memberType, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuthUserInfo(new GoogleOAuthUserInfo(attributes))
                .build();
    }

    public Member toEntity(MemberType memberType, OAuthUserInfo oAuthUserInfo) {
        return Member.builder()
                .memberType(memberType)
                .socialId(oAuthUserInfo.getId())
                .email(UUID.randomUUID() + "@socialUser.com")
                .nickname(oAuthUserInfo.getNickname())
                .imageUrl(oAuthUserInfo.getImageUrl())
                .role(MemberRole.GUEST)
                .build();

    }
}
