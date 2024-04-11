package SniffStep.mapper;

import SniffStep.dto.JoinDTO;
import SniffStep.dto.JoinDTO.JoinDTOBuilder;
import SniffStep.entity.Member;
import SniffStep.entity.Member.MemberBuilder;
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
public class JoinMapperImpl implements JoinMapper {

    @Override
    public JoinDTO toDto(Member e) {
        if ( e == null ) {
            return null;
        }

        JoinDTOBuilder joinDTO = JoinDTO.builder();

        joinDTO.email( e.getEmail() );
        joinDTO.name( e.getName() );
        joinDTO.nickname( e.getNickname() );
        joinDTO.introduce( e.getIntroduce() );
        joinDTO.phoneNumber( e.getPhoneNumber() );
        joinDTO.password( e.getPassword() );
        joinDTO.memberRole( e.getMemberRole() );

        return joinDTO.build();
    }

    @Override
    public Member toEntity(JoinDTO d) {
        if ( d == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.email( d.getEmail() );
        member.name( d.getName() );
        member.nickname( d.getNickname() );
        member.introduce( d.getIntroduce() );
        member.phoneNumber( d.getPhoneNumber() );
        member.password( d.getPassword() );
        member.memberRole( d.getMemberRole() );

        return member.build();
    }

    @Override
    public List<JoinDTO> toDtoList(List<Member> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<JoinDTO> list = new ArrayList<JoinDTO>( entityList.size() );
        for ( Member member : entityList ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntityList(List<JoinDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( dtoList.size() );
        for ( JoinDTO joinDTO : dtoList ) {
            list.add( toEntity( joinDTO ) );
        }

        return list;
    }
}
