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

    private String title;
    private String description;

    private String activityLocation;

    private List<String> activityDate = new ArrayList<>();
    private List<String> activityTime = new ArrayList<>();

    private List<MultipartFile> imageFiles = new ArrayList<>();

    private List<Long> deletedImages = new ArrayList<>();
}

