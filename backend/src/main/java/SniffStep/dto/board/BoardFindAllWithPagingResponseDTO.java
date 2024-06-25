package SniffStep.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardFindAllWithPagingResponseDTO {

    private List<BoardFindAllResponseDTO> data;
    private PageInfoDTO pageInfo;

    public static BoardFindAllWithPagingResponseDTO toDto(List<BoardFindAllResponseDTO> data, PageInfoDTO pageInfo) {
        return new BoardFindAllWithPagingResponseDTO(data, pageInfo);
    }
}
