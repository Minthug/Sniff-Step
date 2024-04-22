package SniffStep.mapper;

import SniffStep.dto.BoardRequestDTO;
import SniffStep.dto.BoardRequestDTO.BoardRequestDTOBuilder;
import SniffStep.entity.Board;
import SniffStep.entity.Board.BoardBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-22T20:11:29+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class BoardRequestMapperImpl implements BoardRequestMapper {

    @Override
    public BoardRequestDTO toDto(Board e) {
        if ( e == null ) {
            return null;
        }

        BoardRequestDTOBuilder boardRequestDTO = BoardRequestDTO.builder();

        boardRequestDTO.title( e.getTitle() );
        boardRequestDTO.description( e.getDescription() );
        boardRequestDTO.activityLocation( e.getActivityLocation() );
        boardRequestDTO.activityDate( e.getActivityDate() );
        boardRequestDTO.activityTime( e.getActivityTime() );

        return boardRequestDTO.build();
    }

    @Override
    public Board toEntity(BoardRequestDTO d) {
        if ( d == null ) {
            return null;
        }

        BoardBuilder board = Board.builder();

        board.title( d.getTitle() );
        board.description( d.getDescription() );
        board.activityLocation( d.getActivityLocation() );
        board.activityDate( d.getActivityDate() );
        board.activityTime( d.getActivityTime() );

        return board.build();
    }

    @Override
    public List<BoardRequestDTO> toDtoList(List<Board> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BoardRequestDTO> list = new ArrayList<BoardRequestDTO>( entityList.size() );
        for ( Board board : entityList ) {
            list.add( toDto( board ) );
        }

        return list;
    }

    @Override
    public List<Board> toEntityList(List<BoardRequestDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Board> list = new ArrayList<Board>( dtoList.size() );
        for ( BoardRequestDTO boardRequestDTO : dtoList ) {
            list.add( toEntity( boardRequestDTO ) );
        }

        return list;
    }
}
