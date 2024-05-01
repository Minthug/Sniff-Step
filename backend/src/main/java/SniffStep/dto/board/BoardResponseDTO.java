package SniffStep.dto.board;

import SniffStep.dto.ImageResponseDTO;
import SniffStep.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDTO {

    private Long id;
    private String author;
    private String title;
    private String description;
    private String activityLocation;

    private List<ImageResponseDTO> images;
    private LocalDateTime createAt;

    public static BoardResponseDTO toDto(String author, Board board) {
        return new BoardResponseDTO(
                board.getId(),
                author,
                board.getTitle(),
                board.getDescription(),
                board.getActivityLocation(),
                board.getImages().stream().map(i -> ImageResponseDTO.toDto(i)).toList(),
                board.getCreatedAt()
        );
    }

}
