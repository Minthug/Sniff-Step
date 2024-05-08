package SniffStep.common.config.oauth;

import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       OAuth2User oAuth2User = super.loadUser(userRequest);
       log.info("getAttributes : {}", oAuth2User.getAttributes());

       String provider = userRequest.getClientRegistration().getRegistrationId();

       OAuth2UserInfo oAuth2UserInfo = null;

       if (provider.equals("google")) {
           log.info("Google Login Request");
              oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
       }

       String providerId = oAuth2UserInfo.getProviderId();
       String email = oAuth2UserInfo.getEmail();
       String loginId = provider + "_" + providerId;
       String name = oAuth2UserInfo.getName();

       Member findMember = memberRepository.findByLoginId(loginId);
       Member member;

       if (findMember == null) {
           member = Member.builder()
                   .loginId(loginId)
                   .name(name)
                   .provider(provider)
                   .providerId(providerId)
                   .role(MemberRole.USER)
                   .build();
           memberRepository.save(member);
       } else {
           member = findMember;
       }
       return new CustomOAuth2UserDetails(member, oAuth2User.getAttributes());
    }
}
