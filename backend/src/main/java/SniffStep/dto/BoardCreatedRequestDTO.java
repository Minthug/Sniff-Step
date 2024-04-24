package SniffStep.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardCreatedRequestDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;
    private List<MultipartFile> images = new ArrayList<>();

}