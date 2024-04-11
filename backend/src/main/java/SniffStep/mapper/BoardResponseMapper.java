package SniffStep.mapper;

import SniffStep.common.GenericMapper;
import SniffStep.dto.BoardResponseDTO;
import SniffStep.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardResponseMapper extends GenericMapper<BoardResponseDTO, Board> {
    BoardResponseMapper INSTANCE = Mappers.getMapper(BoardResponseMapper.class);
}
