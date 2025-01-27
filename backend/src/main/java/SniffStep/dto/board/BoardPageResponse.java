package SniffStep.dto.board;


import java.util.List;

// BoardFindAllWithPagingResponseDTO 대체하는 클래스
public record BoardPageResponse(List<BoardFindAllResponse> data,
                                PageInfo pageInfo,
                                String keyword) {

    public BoardPageResponse(List<BoardFindAllResponse> data, PageInfo pageInfo) {
        this(data, pageInfo, null);
    }

    public static BoardPageResponse of(List<BoardFindAllResponse> data, PageInfo pageInfo) {
        return new BoardPageResponse(data, pageInfo);
    }

    public static BoardPageResponse of(List<BoardFindAllResponse> data, PageInfo pageInfo, String keyword) {
        return new BoardPageResponse(data, pageInfo, keyword);
    }
}
