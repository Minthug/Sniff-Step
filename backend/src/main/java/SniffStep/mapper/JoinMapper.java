package SniffStep.mapper;

import SniffStep.common.GenericMapper;
import SniffStep.dto.JoinDTO;
import SniffStep.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JoinMapper extends GenericMapper<JoinDTO, Member> {

    JoinMapper INSTANCE = Mappers.getMapper(JoinMapper.class);
}
