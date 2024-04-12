package SniffStep.dto;

import SniffStep.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardPatchDTO {
    private String title;
    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;

    @Builder
    public BoardPatchDTO(String title, String description, String activityLocation, String activityDate, String activityTime) {
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .description(description)
                .activityLocation(activityLocation)
                .activityDate(activityDate)
                .activityTime(activityTime)
                .build();
    }
}

