package SniffStep.dto;

import SniffStep.common.BaseTime;
import SniffStep.entity.Board;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;

    private List<String> imgUrl;

    public BoardResponseDTO(Board board, List<String> savedImgUrlList) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.activityLocation = board.getActivityLocation();
        this.activityDate = board.getActivityDate();
        this.activityTime = board.getActivityTime();
        this.imgUrl = savedImgUrlList;
    }

    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.activityLocation = board.getActivityLocation();
        this.activityDate = board.getActivityDate();
        this.activityTime = board.getActivityTime();
    }
}
