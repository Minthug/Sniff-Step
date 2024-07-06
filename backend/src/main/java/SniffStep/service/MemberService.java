package SniffStep.service;

import SniffStep.common.exception.AccessDeniedException;
import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.board.AwsS3;
import SniffStep.dto.member.MemberDTO;
import SniffStep.dto.member.MemberUpdateDTO;
import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsService awsService;

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


    @Transactional
    public void editMember(Long id, MemberUpdateDTO memberUpdateDTO) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();


            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new MemberNotFoundException());

            String encryptedPassword = null;
            if (memberUpdateDTO.getPassword() != null) {
                encryptedPassword = passwordEncoder.encode(memberUpdateDTO.getPassword());
            }

            String imageUrl = null;
            if (!memberUpdateDTO.getImageFiles().isEmpty()) {
                AwsS3 uploadFiles = awsService.uploadProfileFiles(member.getId(), memberUpdateDTO.getImageFiles().get(0));

            }
                member.updateMember(memberUpdateDTO.getNickname(), memberUpdateDTO.getIntroduce(), encryptedPassword,
                        memberUpdateDTO.getPhoneNumber(), imageUrl);
        }
    }

    @Transactional
    public void editMemberV2(Long id, MemberUpdateDTO memberUpdateDTO) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException());

        // 현재 사용자 인증 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails) ||
                !((UserDetails) authentication.getPrincipal()).getUsername().equals(member.getEmail())) {
            throw new AccessDeniedException("You don't have permission to edit this member");
        }

        String encryptedPassword = null;
        if (memberUpdateDTO.getPassword() != null && !memberUpdateDTO.getPassword().isEmpty()) {
            encryptedPassword = passwordEncoder.encode(memberUpdateDTO.getPassword());
        }

        String updatedImageUrl = member.getImageUrl();
        if (memberUpdateDTO.getImageFiles() != null && !memberUpdateDTO.getImageFiles().isEmpty()) {
            MultipartFile profileImage = memberUpdateDTO.getImageFiles().get(0);
            if (!profileImage.isEmpty()) {
                AwsS3 uploadedImage = awsService.uploadProfileFiles(member.getId(), profileImage);
                if (uploadedImage != null ) {
                    updatedImageUrl = uploadedImage.getUploadFileUrl();
                }
            }
        }

        member.updateMember(
                memberUpdateDTO.getNickname(),
                memberUpdateDTO.getIntroduce(),
                memberUpdateDTO.getPhoneNumber(),
                encryptedPassword,
                updatedImageUrl
        );
        memberRepository.save(member);
    }

        @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException());

        memberRepository.delete(member);
    }

}
