package SniffStep.dto.board;

import java.util.List;

public record BoardDetailResponse(Long id, Long userId, String title, String email, String description,
                                  String activityLocation, List<String> activityDate, List<String> activityTime,
                                  String createdAt, String updatedAt, String profileUrl, String nickname, String imageUrl) {
}