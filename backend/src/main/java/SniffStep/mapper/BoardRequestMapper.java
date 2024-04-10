package SniffStep.mapper;

import SniffStep.common.GenericMapper;
import SniffStep.dto.BoardRequestDTO;
import SniffStep.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardRequestMapper extends GenericMapper<BoardRequestDTO, Board> {
    BoardRequestMapper INSTANCE = Mappers.getMapper(BoardRequestMapper.class);
}
