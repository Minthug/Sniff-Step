package SniffStep.dto.board;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public record BoardPatchRequest(String title, String description, String activityLocation,
                                List<String> activityDate, List<String> activityTime,
                                List<MultipartFile> imageFiles, List<Long> deletedImages) {

    public BoardPatchRequest {
    }
}
