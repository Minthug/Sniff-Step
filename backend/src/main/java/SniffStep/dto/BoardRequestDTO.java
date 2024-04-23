package SniffStep.dto;

import SniffStep.entity.Board;
import SniffStep.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class BoardRequestDTO {
    private String title;
    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;
    private List<Long> imageId;

    @Builder
    public BoardRequestDTO(String title, String description, String activityLocation, String activityDate, String activityTime, List<Long> imageId) {
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.imageId = imageId;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .description(description)
                .activityLocation(activityLocation)
                .activityDate(activityDate)
                .activityTime(activityTime)
                .build();
    }
}
