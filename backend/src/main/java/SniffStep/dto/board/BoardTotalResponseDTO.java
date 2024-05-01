package SniffStep.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardTotalResponseDTO {

    private Long id;
    private Long memberId;
    private String nickname;
    private String title;

    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;

    private List<String> imgUrlList;

    @Builder
    public BoardTotalResponseDTO(Long id, Long memberId, String nickname, String title, String description, String activityLocation, String activityDate, String activityTime, List<String> imgUrlList) {
        this.id = id;
        this.memberId = memberId;
        this.nickname = nickname;
        this.title = title;
        this.description = description;
        this.activityLocation = activityLocation;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
        this.imgUrlList = imgUrlList;
    }
}
