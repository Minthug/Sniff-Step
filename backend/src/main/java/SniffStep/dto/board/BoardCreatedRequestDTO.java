package SniffStep.dto.board;

import SniffStep.entity.Board;
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
    private List<Long> imageId;

}
