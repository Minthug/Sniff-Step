package SniffStep.dto;

import SniffStep.common.BaseTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardResponseDTO extends BaseTime {

    private Long id;
    private String title;
    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;
    private MemberDTO author;

    @Builder
    public BoardResponseDTO(Long id, String title, String description, String activityLocation, String activityDate, String activityTime, MemberDTO author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.author = author;
    }
}
