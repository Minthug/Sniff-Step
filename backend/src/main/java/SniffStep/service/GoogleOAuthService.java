package SniffStep.service;

import SniffStep.common.config.oauth.GoogleOAuthClient;
import SniffStep.common.config.oauth.dto.GoogleTokenResponse;
import SniffStep.common.config.oauth.dto.OAuthMember;
import SniffStep.common.config.oauth.dto.TokenResponse;
import SniffStep.common.jwt.JwtTokenProvider;
import SniffStep.common.jwt.entity.Token;
import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import SniffStep.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class GoogleOAuthService {

    private static final int PAYLOAD_INDEX = 1;
    private static final int NAME_BEGIN_INDEX = 0;

    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleOAuthClient googleOAuthClient;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    public TokenResponse createToken(final String code) {
        final GoogleTokenResponse googleTokenResponse = googleOAuthClient.getGoogleAccessToken(code);
        final OAuthMember oAuthMember = createOAuthMember(googleTokenResponse.idToken());
        final Member member = createMemberIfNotExist(oAuthMember);

        final String accessToken = jwtTokenProvider.generateAccessToken(oAuthMember.email());
        final String refreshToken = jwtTokenProvider.generatedRefreshToken(oAuthMember.email());

        saveOrUpdateRefreshToken(member, refreshToken);

        log.info("토큰 생성 - 사용자 이메일 : {} ", oAuthMember.email());
        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveOrUpdateRefreshToken(Member member, String refreshToken) {
       tokenRepository.findByMember(member)
                .ifPresentOrElse(token -> token.changeToken(refreshToken),
                        () -> {
                            tokenRepository.save(new Token(member, refreshToken));
                        }
                );
    }

    private Member createMemberIfNotExist(OAuthMember oAuthMember) {
        Optional<Member> optionalMember = memberRepository.findByEmail(oAuthMember.email());
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        }
        final Member member = new Member(oAuthMember.email(), oAuthMember.displayName(), oAuthMember.imageUrl());
        return memberRepository.save(member);
    }

    private OAuthMember createOAuthMember(String googleIdToken) {
        final Map<String, Object> parsedPayload = parsePayload(googleIdToken);
        final String email = (String) parsedPayload.get("email");
        final String rawName = (String) parsedPayload.get("name");
        final String picture = (String) parsedPayload.get("picture");

//        if (rawName.length() > Name.Max_Length) {
//            final String substringName = rawName.substring(NAME_BEGIN_INDEX, Name.Max_Length);
//            return new OAuthMember(email, substringName, picture);
//        }

        return new OAuthMember(email, rawName, picture);
    }

    private Map<String, Object> parsePayload(String googleIdToken) {
        final String payload = googleIdToken.split("\\.")[PAYLOAD_INDEX];
        final String decodedPayload = new String(Base64.getUrlDecoder().decode(payload));
        final JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();

        return jacksonJsonParser.parseMap(decodedPayload);
    }
}
