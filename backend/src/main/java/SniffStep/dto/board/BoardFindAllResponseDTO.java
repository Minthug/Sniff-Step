package SniffStep.dto.board;

import SniffStep.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardFindAllResponseDTO {

    private Long id;
    private String title;
    private String nickname;

    public static BoardFindAllResponseDTO toDto(Board board) {
        return new BoardFindAllResponseDTO(board.getId(), board.getTitle(), board.getMember().getNickname());
    }
}
