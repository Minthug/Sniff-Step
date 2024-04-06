package SniffStep.mapper;

import SniffStep.common.GenericMapper;
import SniffStep.dto.MemberDTO;
import SniffStep.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper extends GenericMapper<MemberDTO, Member> {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

}
