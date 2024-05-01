package SniffStep.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardFindAllWithPagingResponseDTO {

    private List<BoardFindAllResponseDTO> boards;
    private PageInfoDTO pageInfo;

    public static BoardFindAllWithPagingResponseDTO toDto(List<BoardFindAllResponseDTO> boards, PageInfoDTO pageInfo) {
        return new BoardFindAllWithPagingResponseDTO(boards, pageInfo);
    }
}
