package SniffStep.service;

import SniffStep.common.exception.AccessDeniedException;
import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.auth.ProfileDTO;
import SniffStep.dto.board.AwsS3;
import SniffStep.dto.member.MemberDTO;
import SniffStep.dto.member.MemberResponseDTO;
import SniffStep.dto.member.MemberUpdateDTO;
import SniffStep.entity.Member;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsService awsService;
    private final BoardRepository boardRepository;

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
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException());

        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public ProfileDTO getProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException());
        return ProfileDTO.fromMember(member);
    }

    @Transactional
    public MemberResponseDTO editMemberV3(Long id, MemberUpdateDTO memberUpdateDTO) {
        log.info("Editing member with id: {}", id);
        Member member = findMemberById(id);

        try {
            validateUserPermission(member);

            String encryptedPassword = encryptPasswordIfProvided(memberUpdateDTO.getPassword());
            AwsS3 updatedImage = updateProfileImage(member, memberUpdateDTO.getImageFiles());
            updateMemberDetails(member, memberUpdateDTO, encryptedPassword, updatedImage.getUploadFileUrl());

            memberRepository.save(member);
            log.info("Member updated successfully. Member url: {}", member.getImageUrl());

            return MemberResponseDTO.of(member);
        } catch (Exception e) {
            log.error("Failed to update member", e);
            throw new RuntimeException("Failed to update member", e);
        }
    }

    private void validateUserPermission(Member member) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails) ||
                !((UserDetails) authentication.getPrincipal()).getUsername().equals(member.getEmail())) {
            throw new AccessDeniedException("You don't have permission to edit this member");
        }
    }

    private Member findMemberById(Long id) {
       return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException());
    }

    private String encryptPasswordIfProvided(String password) {
        if (password != null && !password.isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(password);
            log.info("Password encrypted: {}", encryptedPassword);
            return encryptedPassword;
        }
        return null;
    }

    private AwsS3 updateProfileImage(Member member, List<MultipartFile> imageFile) {
        if (imageFile == null || imageFile.isEmpty() || imageFile.get(0).isEmpty()) {
            log.info("No image file provided. Keeping existing image url.");
            return null;
        }
        try {
            String existingImageUrl = member.getImageUrl();
            if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
                boolean deleted = deleteExistingImage(existingImageUrl);
                if (!deleted) {
                    log.warn("Failed to delete old profile image: {}", existingImageUrl);
                }
            }

            MultipartFile profileImage = imageFile.get(0);
            AwsS3 uploadedImage = awsService.uploadProfileV3(member.getId(), profileImage);
            log.info("New image uploaded. Updated image Url: {}", member.getId(), uploadedImage.getUploadFileUrl());
            return uploadedImage;
        } catch (Exception e) {
            log.error("Failed to upload profile image", e);
            throw new RuntimeException("Failed to upload profile image", e);
        }
    }

    private boolean deleteExistingImage(String imageUrl) {
        if (imageUrl == null && imageUrl.isEmpty()) {
            log.debug("No existing image to delete");
            return true;
        }

        try {
            boolean deleted = awsService.deleteFileV3(imageUrl);
            if (deleted) {
                log.info("Old profile image deleted successfully: {}", imageUrl);
                return true;
            } else {
                log.warn("Failed to delete old profile image: {}", imageUrl);
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to delete old profile image: {}", imageUrl, e);
            return false;
        }
    }


    private void updateMemberDetails(Member member, MemberUpdateDTO memberUpdateDTO, String encryptedPassword, String updatedImageUrl) {
        member.updateMember(
                memberUpdateDTO.getNickname(),
                memberUpdateDTO.getIntroduce(),
                memberUpdateDTO.getPhoneNumber(),
                encryptedPassword,
                updatedImageUrl
        );
        member.updateProfileImageUrl(updatedImageUrl);
    }
}
