package SniffStep.service;

import SniffStep.common.exception.AccessDeniedException;
import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.auth.ProfileRequest;
import SniffStep.dto.board.AwsS3;
import SniffStep.dto.member.MemberDetailResponse;
import SniffStep.dto.member.MemberResponse;
import SniffStep.dto.member.MemberUpdateResponse;
import SniffStep.entity.Member;
import SniffStep.repository.BoardRepository;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AwsService awsService;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<MemberDetailResponse> findAllMember() {
        return memberRepository.findAll().stream()
                .map(MemberDetailResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findMember(Long id) {
        return memberRepository.findById(id)
                .map(MemberDetailResponse::from)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = findMemberById(id);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public ProfileRequest getProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with email: " + email));
        return ProfileRequest.fromMember(member);
    }

    @Transactional
    public MemberResponse editMember(Long id, MemberUpdateResponse updateResponse) {
        log.info("Editing member with id: {}", id);
        Member member = findMemberById(id);

        try {
            validateUserPermission(member);

            String encryptedPassword = encryptPasswordIfProvided(updateResponse.password());
            AwsS3 updatedImage = updateProfileImage(member, updateResponse.imageFiles());
            updateMemberDetails(member, updateResponse, encryptedPassword, updatedImage.getUploadFileUrl());

            memberRepository.save(member);
            log.info("Member updated successfully. Member url: {}", member.getImageUrl());

            return MemberResponse.of(member);
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
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));
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


    private void updateMemberDetails(Member member, MemberUpdateResponse updateResponse, String encryptedPassword, String updatedImageUrl) {
        member.updateMember(
                updateResponse.nickname(),
                updateResponse.introduce(),
                updateResponse.phoneNumber(),
                encryptedPassword,
                updatedImageUrl
        );
        member.updateProfileImageUrl(updatedImageUrl);
    }
}
