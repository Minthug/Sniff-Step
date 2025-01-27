package SniffStep.dto.board;

import SniffStep.dto.member.MemberDetailResponse;
import SniffStep.entity.ActivityDate;
import SniffStep.entity.ActivityTime;
import SniffStep.entity.Board;

import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record BoardFindAllResponse(Long id, Long userId, String title, String email,
                                   String description, String activityLocation,
                                   List<String> activityDate, List<String> activityTime,
                                   String createdAt, String updatedAt, String profileUrl,
                                   String nickname, String image) {

    public static BoardFindAllResponse from(Board board) {
        MemberDetailResponse memberDetailResponse = MemberDetailResponse.from(board.getMember());

        String image = board.getImages().stream()
                .map(img -> ImageResponseDTO.toDto(img).getImageUrl())
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        return new BoardFindAllResponse(
                board.getId(),
                memberDetailResponse.id(),
                board.getTitle(),
                memberDetailResponse.email(),
                board.getDescription(),
                board.getActivityLocation(),
                board.getActivityDates().stream().map(ActivityDate::name).collect(Collectors.toList()),
                board.getActivityTimes().stream().map(ActivityTime::name).collect(Collectors.toList()),
                board.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toString(),
                board.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toString(),
                memberDetailResponse.imageUrl(),
                memberDetailResponse.nickname(),
                image
        );
    }
}