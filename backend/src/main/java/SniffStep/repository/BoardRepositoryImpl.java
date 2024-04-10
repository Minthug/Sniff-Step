package SniffStep.repository;

import SniffStep.dto.BoardResponseDTO;
import SniffStep.entity.Board;
import SniffStep.entity.BoardType;
import SniffStep.mapper.BoardResponseMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static SniffStep.entity.QBoard.board;
import static SniffStep.entity.QMember.member;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageImpl<BoardResponseDTO> getBoardList(String query, BoardType boardType, Pageable pageable) {
        List<Board> boardList = queryFactory
                .selectFrom(board)
                .leftJoin(board.author).fetchJoin()
                .where(board.title.contains(query), board.boardType.eq(boardType))
                .fetch();

        Long count = queryFactory
                .select(board)
                .from(board)
                .where(board.title.contains(query), board.boardType.eq(boardType))
                .fetchCount();

        List<BoardResponseDTO> boardResponseDTOList = BoardResponseMapper.INSTANCE.toDtoList(boardList);

        return new PageImpl<>(boardResponseDTOList, pageable, count);
    }

    @Override
    public List<BoardResponseDTO> getBestList(BoardType boardType) {
        List<Board> boardList = queryFactory
                .selectFrom(board)
                .leftJoin(board.author, member).fetchJoin()
                .where(board.boardType.eq(boardType))
                .orderBy(board.id/*likeCount*/.desc())
                .limit(20)
                .fetch();
        return BoardResponseMapper.INSTANCE.toDtoList(boardList);
    }
}
