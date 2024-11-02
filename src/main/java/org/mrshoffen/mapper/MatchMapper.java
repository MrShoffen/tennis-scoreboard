package org.mrshoffen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mrshoffen.dto.response.pageable.MatchResponseDto;
import org.mrshoffen.entity.Match;

@Mapper
public interface MatchMapper {


    @Mapping(target = "firstPlayer", source = "firstPlayer.name")
    @Mapping(target = "secondPlayer", source = "secondPlayer.name")
    @Mapping(target = "winner", source = "winner.name")
    MatchResponseDto toDto(Match match);
}
