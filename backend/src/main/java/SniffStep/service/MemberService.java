package SniffStep.service;

import SniffStep.common.exception.AccessDeniedException;
import SniffStep.common.exception.MemberNotFoundException;
import SniffStep.dto.auth.ProfileDTO;
import SniffStep.dto.board.AwsS3;
import SniffStep.dto.member.MemberDTO;
import SniffStep.dto.member.MemberResponseDTO;
import SniffStep.dto.member.MemberUpdateDTO;
import SniffStep.entity.Member;
import SniffStep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
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
    public MemberResponseDTO editMemberV2(Long id, MemberUpdateDTO memberUpdateDTO) {

        log.info("Editing member with id: {}", id);
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
            log.info("Password encrypted: {}", encryptedPassword);
        }

        String updatedImageUrl = member.getImageUrl();
        log.info("Current image url: {}", updatedImageUrl);

        if (memberUpdateDTO.getImageFiles() != null && !memberUpdateDTO.getImageFiles().isEmpty()) {
            MultipartFile profileImage = memberUpdateDTO.getImageFiles().get(0);
            if (!profileImage.isEmpty()) {
                try {
                    // 기존 이미지 삭제
                    if (updatedImageUrl != null && !updatedImageUrl.isEmpty()) {
                        boolean deleted = awsService.deleteFileV2(extractFilePath(updatedImageUrl), extractFileName(updatedImageUrl));
                        if (deleted) {
                            log.info("Old profile image deleted successfully: {}", updatedImageUrl);
                        } else {
                            log.warn("Failed to delete old profile image: {}", updatedImageUrl);
                        }
                    }
                    log.info("Uploading new profile image: {}", profileImage.getOriginalFilename());
                    AwsS3 uploadedImage = awsService.uploadProfileFilesV2(member.getId(), profileImage);
                    updatedImageUrl = uploadedImage.getUploadFileUrl();
                    log.info("New image uploaded. Updated image Url: {}", updatedImageUrl);
                } catch (Exception e) {
                    log.error("Failed to upload profile image", e);
                    throw new RuntimeException("Failed to upload profile image", e);
                }
            } else {
                log.info("Provided image file is empty. Keeping existing image url.");
            }
        } else {
            log.info("No image file provided. Keeping existing image url.");
        }

        member.updateMember(
                memberUpdateDTO.getNickname(),
                memberUpdateDTO.getIntroduce(),
                memberUpdateDTO.getPhoneNumber(),
                encryptedPassword,
                updatedImageUrl
        );

        member.updateProfileImageUrl(updatedImageUrl);
        memberRepository.save(member);
        log.info("Member updated successfully. Member url: {}", member.getImageUrl());

        return MemberResponseDTO.of(member);
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

    private String extractFilePath(String s3url) {
        try {
            URL url = new URL(s3url);
            String path = url.getPath();


            int lastSlashIndex = path.lastIndexOf("/");
            if (lastSlashIndex > 0) {
                return path.substring(1, lastSlashIndex);
            } else {
                throw new IllegalArgumentException("Invalid s3 URL format: {}" + s3url);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + s3url, e);
        }
    }

    private String extractFileName(String s3Url) {
        try {
            URL url = new URL(s3Url);
            String path = url.getPath();
            int lastSlashIndex = path.lastIndexOf("/");
            if (lastSlashIndex >= 0 && lastSlashIndex < path.length() - 1) {
                return path.substring(lastSlashIndex + 1);
            } else {
                throw new IllegalArgumentException("Invalid s3 URL format: {}" + s3Url);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + s3Url, e);
        }
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
