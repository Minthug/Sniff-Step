package SniffStep.service;

import SniffStep.dto.MemberPostDTO;
import SniffStep.dto.MemberResponseDTO;
import SniffStep.dto.MemberUpdateDTO;
import SniffStep.entity.Member;
import SniffStep.entity.MemberRole;
import SniffStep.entity.MemberType;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


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
