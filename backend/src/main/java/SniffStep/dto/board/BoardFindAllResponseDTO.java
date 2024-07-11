    package SniffStep.dto.board;

    import SniffStep.dto.member.MemberDTO;
    import SniffStep.entity.ActivityDate;
    import SniffStep.entity.ActivityTime;
    import SniffStep.entity.Board;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.ZoneId;
    import java.util.List;
    import java.util.Objects;
    import java.util.stream.Collectors;

    @Getter
    @NoArgsConstructor
    @Data
    @AllArgsConstructor
    public class BoardFindAllResponseDTO {

        private Long id;
        private Long userId;
        private String title;
        private String email;
        private String description;
        private String activityLocation;
        private List<String> activityDate;
        private List<String> activityTime;
        private String createdAt;
        private String updatedAt;
        private String profileUrl;
        private String nickname;
        private String image;


        public static BoardFindAllResponseDTO toDto(Board board) {
            MemberDTO memberDTO = MemberDTO.toDto(board.getMember());

            String image = board.getImages().stream()
                    .map(img -> ImageResponseDTO.toDto(img).getImageUrl())
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);

            return new BoardFindAllResponseDTO(
                    board.getId(),
                    memberDTO.getId(),
                    board.getTitle(),
                    memberDTO.getEmail(),
                    board.getDescription(),
                    board.getActivityLocation(),
                    board.getActivityDates().stream().map(ActivityDate::name).collect(Collectors.toList()),
                    board.getActivityTimes().stream().map(ActivityTime::name).collect(Collectors.toList()),
                    board.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toString(),
                    board.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toString(),
                    memberDTO.getImageUrl(),
                    memberDTO.getNickname(),
                    image
            );
        }
    }
