package SniffStep.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoDTO {
    private int totalPage;
    private int nowPage;
    private int numberOfElements;
    private boolean isNext;

    public PageInfoDTO(Page result) {
        this.totalPage = result.getTotalPages();
        this.nowPage = result.getNumber();
        this.numberOfElements = result.getNumberOfElements();
        this.isNext = result.hasNext();
    }
}
