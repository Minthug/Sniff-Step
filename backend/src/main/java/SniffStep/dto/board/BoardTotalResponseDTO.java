package SniffStep.dto.board;

import SniffStep.entity.ActivityDate;
import SniffStep.entity.ActivityTime;
import SniffStep.entity.Board;
import SniffStep.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardTotalResponseDTO {

    private Long id;
    private Long memberId;
    private String nickname;
    private String title;

    private String description;
    private String activityLocation;
    private String activityDate;
    private String activityTime;

    private List<String> images;

    @Builder
    public BoardTotalResponseDTO(Long id, Long id1, String nickname, String title, String description, String activityLocation, ActivityDate activityDate, ActivityTime activityTime, List<Image> images) {
    }

    public static BoardTotalResponseDTO toDto(Board board) {
        return new BoardTotalResponseDTO(
                board.getId(),
                board.getMember().getId(),
                board.getMember().getNickname(),
                board.getTitle(),
                board.getDescription(),
                board.getActivityLocation(),
                board.getActivityDate(),
                board.getActivityTime(),
                board.getImages());
    }
}
