package SniffStep.common.config.oauth;

import SniffStep.entity.Member;
import SniffStep.entity.MemberType;
import SniffStep.entity.OAuthUser;
import SniffStep.repository.MemberRepository;
import SniffStep.repository.OAuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final OAuthUserRepository oAuthUserRepository;

//    private static final String NAVER = "naver";
//    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        MemberType memberType = getMemberType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(memberType, userNameAttributeName, attributes);

        Member createMember = getMember(extractAttributes, memberType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createMember.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createMember.getEmail(),
                createMember.getRole()
        );
    }

    private Member getMember(OAuthAttributes attributes, MemberType memberType) {
        Member findMember = memberRepository.findByMemberTypeAndSocialId(memberType,
                attributes.getOAuth2UserInfo().getId()).orElse(null);

        if (findMember == null) {
            return saveUser(attributes, memberType);
        }
        return findMember;
    }


    private MemberType getMemberType(String registrationId) {
        return MemberType.GOOGLE;
    }

    @Transactional
    public Member saveUser(OAuthAttributes attributes, MemberType memberType) {
        Member createdMember = attributes.toEntity(memberType, attributes.getOAuth2UserInfo());

        OAuthUser oAuthUser = OAuthUser.builder()
                .email(createdMember.getEmail())
                .provider(memberType.toString())
                .providerId(attributes.getOAuth2UserInfo().getId())
                .member(createdMember)
                .build();

        log.info("Saving OAuthUser: {}", oAuthUser);
        OAuthUser savedOAuthUser = oAuthUserRepository.save(oAuthUser);
        log.info("Saving OAuthUser: {}", savedOAuthUser);

        log.info("Saving Member: {}", createdMember);
        Member savedMember = memberRepository.save(createdMember);
        log.info("Saved Member: {}", savedMember);

        return savedMember;
    }

}
