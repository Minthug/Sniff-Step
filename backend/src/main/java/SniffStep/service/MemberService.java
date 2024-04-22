package SniffStep.service;

import SniffStep.common.exception.DuplicateResourceException;
import SniffStep.common.jwt.JwtTokenProvider;
import SniffStep.common.jwt.dto.TokenResponseDTO;
import SniffStep.common.jwt.entity.RefreshToken;
import SniffStep.common.jwt.repository.LogoutTokenRedisRepository;
import SniffStep.common.jwt.repository.RefreshTokenRedisRepository;
import SniffStep.dto.*;
import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.entity.MemberType;
import SniffStep.mapper.JoinMapper;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static SniffStep.common.jwt.JwtTokenExpireEnum.REFRESH_TOKEN_EXPIRE_TIME;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JoinMapper joinMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final BoardRepository boardRepository;
    private final ImageService imageService;
    private final LogoutTokenRedisRepository logoutTokenRedisRepository;

    private static final String HEADER_PREFIX = "Bearer ";

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Member not found."));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }


    // View one member
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Member not found."));
    }
    // Sign up
    @Transactional
    public Member join(JoinDTO joinDTO) {
        boolean isExist = memberRepository.existsByEmail(joinDTO.getEmail());

        if (isExist) {
            throw new DuplicateResourceException("Email is already in use.");
        }

        Member member = joinMapper.toEntity(joinDTO);
        hashAndSetPassword(member);

        return memberRepository.save(member);
    }

    private RefreshToken saveRefreshToken(String username) {
        return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(username,
                jwtTokenProvider.generateRefreshToken(username), REFRESH_TOKEN_EXPIRE_TIME.getValue()));
    }
    @Transactional(readOnly = true)
    public TokenResponseDTO login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        Member member = findByEmail(email);

        if (!passwordEncoder.matches(loginDTO.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("Invalid password.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(email);
        RefreshToken refreshToken = saveRefreshToken(email);

        return TokenResponseDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public TokenResponseDTO reissue(String refreshToken) {
        // header
        refreshToken = resolveToken(refreshToken);
        String username = getCurrentUsername();

        // take refreshToken from redis
        RefreshToken redisRefreshToken = refreshTokenRedisRepository.findById(username)
                .orElseThrow(NoSuchFieldError::new);

        // match refresh token
        if (redisRefreshToken.equals(redisRefreshToken.getRefreshToken())) {
            return reissueRefreshToken(refreshToken, username);
        }
        throw new IllegalArgumentException("Invalid refresh token.");
    }

    private TokenResponseDTO reissueRefreshToken(String refreshToken, String username) {

        if (lessThanReissueExpirationTimesLeft(refreshToken)) {
            String accessToken = jwtTokenProvider.generateAccessToken(username);
            return TokenResponseDTO
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(saveRefreshToken(username).getRefreshToken())
                    .build();
        }

        return TokenResponseDTO
                .builder()
                .accessToken(jwtTokenProvider.generateAccessToken(username))
                .refreshToken(refreshToken)
                .build();
    }

    private String resolveToken(String token) {
       return StringUtils.substring(token, 7);
    }

    private boolean lessThanReissueExpirationTimesLeft(String refreshToken) {
        return jwtTokenProvider.getRemainMilliseconds(refreshToken) < REFRESH_TOKEN_EXPIRE_TIME.getValue();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getAuthorities();
        return principal.getUsername();
    }


    private void hashAndSetPassword(Member member) {
        if (member.getPassword() == null) {
            throw new IllegalArgumentException("비밀번호는 null이 될 수 없습니다");
        }
        member.hashPassword(passwordEncoder);
    }

    // Update member
    public Member update(MemberUpdateDTO memberUpdateDTO, Long id) {
        Member member = findById(id);
        member.updateMember(memberUpdateDTO);
        member.hashPassword(passwordEncoder);
        return member;
    }

    public Member delete(Long id) {
        Member member = findById(id);
        memberRepository.delete(member);
        return member;
    }

    public MemberResponseDTO registerGeneral(MemberPostDTO request) {
        Member newMember = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .type(MemberType.GENERAL)
                .role(MemberRole.USER)
                .build();

        Member createdMember = memberRepository.save(newMember);
        return MemberResponseDTO.of(createdMember);
    }

}
