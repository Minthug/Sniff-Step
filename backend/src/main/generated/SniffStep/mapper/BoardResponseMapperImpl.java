package SniffStep.mapper;

import SniffStep.dto.BoardResponseDTO;
import SniffStep.dto.BoardResponseDTO.BoardResponseDTOBuilder;
import SniffStep.dto.MemberDTO;
import SniffStep.dto.MemberDTO.MemberDTOBuilder;
import SniffStep.entity.Board;
import SniffStep.entity.Member;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-11T17:24:36+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class BoardResponseMapperImpl implements BoardResponseMapper {

    @Override
    public BoardResponseDTO toDto(Board e) {
        if ( e == null ) {
            return null;
        }

        BoardResponseDTOBuilder boardResponseDTO = BoardResponseDTO.builder();

        boardResponseDTO.id( e.getId() );
        boardResponseDTO.title( e.getTitle() );
        boardResponseDTO.description( e.getDescription() );
        boardResponseDTO.activityLocation( e.getActivityLocation() );
        if ( e.getActivityDate() != null ) {
            boardResponseDTO.activityDate( DateTimeFormatter.ISO_LOCAL_DATE.format( e.getActivityDate() ) );
        }
        if ( e.getActivityTime() != null ) {
            boardResponseDTO.activityTime( DateTimeFormatter.ISO_LOCAL_TIME.format( e.getActivityTime() ) );
        }
        boardResponseDTO.author( memberToMemberDTO( e.getAuthor() ) );

        return boardResponseDTO.build();
    }

    @Override
    public Board toEntity(BoardResponseDTO d) {
        if ( d == null ) {
            return null;
        }

        Board board = new Board();

        return board;
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

    protected MemberDTO memberToMemberDTO(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.id( member.getId() );
        memberDTO.email( member.getEmail() );
        memberDTO.name( member.getName() );
        memberDTO.nickname( member.getNickname() );
        memberDTO.introduce( member.getIntroduce() );
        memberDTO.phoneNumber( member.getPhoneNumber() );
        memberDTO.memberRole( member.getMemberRole() );

        return memberDTO.build();
    }
}
