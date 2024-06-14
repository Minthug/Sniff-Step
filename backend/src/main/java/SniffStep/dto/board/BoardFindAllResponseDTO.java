    package SniffStep.dto.board;

    import SniffStep.entity.Board;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.NoArgsConstructor;

    import java.time.LocalDateTime;
    import java.util.List;

    @Getter
    @NoArgsConstructor
    public class BoardFindAllResponseDTO {

        private Long id;
        private Long userId;
        private String title;
        private String email;
        private String description;
        private String activityLocation;
        private List<String> ActivityDate;
        private List<String> ActivityTime;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String profileImageUrl;
        private String nickname;

        @Builder
        public BoardFindAllResponseDTO(Long id, Long userId, String title, String email, String description, String activityLocation, List<String> activityDate, List<String> activityTime, LocalDateTime createdAt, LocalDateTime updatedAt, String profileImageUrl,String nickname) {
            this.id = id;
            this.userId = userId;
            this.title = title;
            this.email = email;
            this.description = description;
            this.activityLocation = activityLocation;
            ActivityDate = activityDate;
            ActivityTime = activityTime;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.profileImageUrl = profileImageUrl;
            this.nickname = nickname;
        }


        public static BoardFindAllResponseDTO toDto(Board board) {
            return BoardFindAllResponseDTO.builder()
                    .id(board.getId())
                    .userId(board.getMember().getId())
                    .title(board.getTitle())
                    .email(board.getMember().getEmail())
                    .description(board.getDescription())
                    .activityDate(convertActivityDateToStringList(board.getActivityDate()))
                    .activityTime(convertActivityTimeToStringList(board.getActivityTime()))
                    .activityLocation(board.getActivityLocation())
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .profileImageUrl(board.getMember().getImageUrl())
                    .nickname(board.getMember().getNickname())
                    .build();
        }

        private static List<String> convertActivityDateToStringList(SniffStep.entity.ActivityDate activityDate) {
            if (activityDate == null) {
                return null;
            }
            return List.of(activityDate.getValue());
        }

        private static List<String> convertActivityTimeToStringList(SniffStep.entity.ActivityTime activityTime) {
            if (activityTime == null) {
                return null;
            }
            return List.of(activityTime.getValue());
        }
    }
