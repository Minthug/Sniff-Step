package SniffStep.service;

import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.auth.LoginDTO;
import SniffStep.dto.auth.SignUpRequestDTO;
import SniffStep.dto.member.MemberDTO;
import SniffStep.dto.member.MemberUpdateDTO;
import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberDTO> findAllMember() {
        return memberRepository.findAll().stream()
                .map(MemberDTO::toDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public MemberDTO findMember(Long id) {
        return memberRepository.findById(id)
                .map(MemberDTO::toDto)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public MemberDTO findByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        return MemberDTO.toDto(member);
    }

    @Transactional
    public void editMember(Member member, MemberUpdateDTO memberUpdateDTO) {
        member.updateMember(
                passwordEncoder.encode(memberUpdateDTO.getPassword()),
                memberUpdateDTO.getNickname(),
                memberUpdateDTO.getIntroduce());
    }

    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }


    public Member getLoginMemberById(Long memberId) {
        if (memberId == null) {
            return null;
        }

        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);
    }

}
