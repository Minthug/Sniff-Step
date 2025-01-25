package SniffStep.dto.member;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record MemberUpdateResponse(String nickname, String introduce, String password, String phoneNumber,
                                   List<MultipartFile> imageFiles, List<Long> imageId) {

    public MemberUpdateResponse {
        if (imageFiles == null) imageFiles = new ArrayList<>();
    }
}
