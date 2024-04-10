package SniffStep.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDTO {
    private String title;
    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;
    private Long memberId;

    public BoardRequestDTO(String title, String description, String activityLocation, String activityDate, String activityTime, Long memberId) {
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.memberId = memberId;
    }
}
