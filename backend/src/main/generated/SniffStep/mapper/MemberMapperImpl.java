package SniffStep.mapper;

import SniffStep.dto.MemberDTO;
import SniffStep.dto.MemberDTO.MemberDTOBuilder;
import SniffStep.entity.Member;
import SniffStep.entity.Member.MemberBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-22T20:11:28+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDTO toDto(Member e) {
        if ( e == null ) {
            return null;
        }

        MemberDTOBuilder memberDTO = MemberDTO.builder();

        memberDTO.id( e.getId() );
        memberDTO.email( e.getEmail() );
        memberDTO.name( e.getName() );
        memberDTO.nickname( e.getNickname() );
        memberDTO.introduce( e.getIntroduce() );
        memberDTO.phoneNumber( e.getPhoneNumber() );

        return memberDTO.build();
    }

    @Override
    public Member toEntity(MemberDTO d) {
        if ( d == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.id( d.getId() );
        member.email( d.getEmail() );
        member.name( d.getName() );
        member.nickname( d.getNickname() );
        member.introduce( d.getIntroduce() );
        member.phoneNumber( d.getPhoneNumber() );

        return member.build();
    }

    @Override
    public List<MemberDTO> toDtoList(List<Member> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MemberDTO> list = new ArrayList<MemberDTO>( entityList.size() );
        for ( Member member : entityList ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntityList(List<MemberDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( dtoList.size() );
        for ( MemberDTO memberDTO : dtoList ) {
            list.add( toEntity( memberDTO ) );
        }

        return list;
    }
}
