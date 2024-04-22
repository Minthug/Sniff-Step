package SniffStep.mapper;

import SniffStep.dto.BoardResponseDTO;
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
public class BoardResponseMapperImpl implements BoardResponseMapper {

    @Override
    public BoardResponseDTO toDto(Board e) {
        if ( e == null ) {
            return null;
        }

        BoardResponseDTO boardResponseDTO = new BoardResponseDTO();

        return boardResponseDTO;
    }

    @Override
    public Board toEntity(BoardResponseDTO d) {
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
    public List<BoardResponseDTO> toDtoList(List<Board> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BoardResponseDTO> list = new ArrayList<BoardResponseDTO>( entityList.size() );
        for ( Board board : entityList ) {
            list.add( toDto( board ) );
        }

        return list;
    }

    @Override
    public List<Board> toEntityList(List<BoardResponseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Board> list = new ArrayList<Board>( dtoList.size() );
        for ( BoardResponseDTO boardResponseDTO : dtoList ) {
            list.add( toEntity( boardResponseDTO ) );
        }

        return list;
    }
}
