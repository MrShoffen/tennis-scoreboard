package org.mrshoffen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.OngoingMatch;

@Mapper
public interface OngoingMatchMapper {

    @Mapping(target = "firstPlayer", source = "firstPlayer")
    @Mapping(target = "secondPlayer", source = "secondPlayer")
    OngoingMatchResponseDto toDto(OngoingMatch ongoingMatch);

}
