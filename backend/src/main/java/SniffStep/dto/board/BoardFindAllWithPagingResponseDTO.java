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
    private String keyword;


    public BoardFindAllWithPagingResponseDTO(List<BoardFindAllResponseDTO> data, PageInfoDTO pageInfo) {
        this(data, pageInfo, null);
    }

    public static BoardFindAllWithPagingResponseDTO toDto(List<BoardFindAllResponseDTO> data, PageInfoDTO pageInfo) {
        return new BoardFindAllWithPagingResponseDTO(data, pageInfo);
    }

    public static BoardFindAllWithPagingResponseDTO toFrom(List<BoardFindAllResponseDTO> data, PageInfoDTO pageInfo, String keyword) {
        return new BoardFindAllWithPagingResponseDTO(data, pageInfo, keyword);
    }

}
