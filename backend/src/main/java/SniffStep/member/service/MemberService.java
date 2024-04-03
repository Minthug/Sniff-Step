package SniffStep.member.service;

import SniffStep.member.entity.Member;
import SniffStep.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // Sign up
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // Exception
    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByName(member.getName());
        if(!findMember.isEmpty()) {
            throw new IllegalStateException("Already existing member.");
        }
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
        member.updateNickname(nickname);
        return member;
    }
}
