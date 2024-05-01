package SniffStep.service;

import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.member.MemberDTO;
import SniffStep.dto.member.MemberUpdateDTO;
import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<MemberDTO> findAllMember() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberDTO::toDto)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public MemberDTO findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
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
}
