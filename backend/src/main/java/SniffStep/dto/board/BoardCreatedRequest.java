package SniffStep.dto.board;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record BoardCreatedRequest(String title, String description, String activityLocation,
                                  List<MultipartFile> images, List<String> activityDate, List<String> activityTime) {
}