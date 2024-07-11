package SniffStep.dto.board;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class BoardPatchDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String description;

    private String activityLocation;

   private List<MultipartFile> imageFiles = new ArrayList<>();

   private List<Long> deletedImages = new ArrayList<>();
}

