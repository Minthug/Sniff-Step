package SniffStep.dto.board;

import SniffStep.entity.ActivityDate;
import SniffStep.entity.ActivityTime;
import SniffStep.entity.Board;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardCreatedRequestDTO {

    private String title;
    private String description;
    private String activityLocation;
    private List<MultipartFile> images = new ArrayList<>();
    private List<String> activityDate = new ArrayList<>();
    private List<String> activityTime = new ArrayList<>();

}
