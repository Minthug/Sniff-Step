package SniffStep.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MemberUpdateDTO {
    private String nickname;
    private String introduce;
    private String password;
    private String phoneNumber;
    private List<MultipartFile> imageFiles = new ArrayList<>();
    private List<Long> imageId;

}
