package SniffStep.dto.board;

import org.springframework.data.domain.Page;

public record PageInfo(int totalPage,
                       int nowPage,
                       int numberOfElements,
                       boolean isNext) {

    public static PageInfo from(Page result) {
        return new PageInfo(
                result.getTotalPages(),
                result.getNumber(),
                result.getNumberOfElements(),
                result.hasNext()
        );
    }
}