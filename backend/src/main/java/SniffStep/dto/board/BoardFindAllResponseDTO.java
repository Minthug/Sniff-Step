    package SniffStep.dto.board;

    import SniffStep.entity.ActivityDate;
    import SniffStep.entity.ActivityTime;
    import SniffStep.entity.Board;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.ZoneId;
    import java.util.List;
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
        private ActivityDate activityDate;
        private ActivityTime activityTime;
        private String createdAt;
        private String updatedAt;
        private String imageUrl;
        private String nickname;
        private List<ImageResponseDTO> images;


        public static BoardFindAllResponseDTO toDto(Board board) {
            return new BoardFindAllResponseDTO(
                    board.getId(),
                    board.getMember().getId(),
                    board.getTitle(),
                    board.getMember().getEmail(),
                    board.getDescription(),
                    board.getActivityLocation(),
                    board.getActivityDate(),
                    board.getActivityTime(),
                    board.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toString(),
                    board.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")).toInstant().toString(),
                    board.getMember().getImageUrl(),
                    board.getMember().getNickname(),
                    board.getImages().stream().map(i -> ImageResponseDTO.toDto(i)).collect(Collectors.toList())
            );
        }
    }
