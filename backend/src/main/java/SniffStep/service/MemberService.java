package SniffStep.service;

import SniffStep.dto.JoinDTO;
import SniffStep.entity.Member;
import SniffStep.mapper.JoinMapper;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JoinMapper joinMapper;

    // Sign up
    @Transactional
    public Member join(JoinDTO joinDTO) {
        boolean isExist = memberRepository.existsByEmail(joinDTO.getEmail());

        if (isExist) {
            throw new IllegalStateException("Email is already in use.");
        }

        Member member = joinMapper.toEntity(joinDTO);
        member.hashPassword(passwordEncoder);

        return memberRepository.save(member);
    }

    // View all members
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // View one member
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("Member not found."));
    }

    // Update member
    public Member update(long memberId, String nickname) {
        Member member = memberRepository.findById(memberId).get();
//        member.updateNickname(nickname);
        return member;
    }
}
