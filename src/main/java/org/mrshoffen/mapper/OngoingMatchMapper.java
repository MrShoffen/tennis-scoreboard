package org.mrshoffen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.domain.OngoingMatch;

@Mapper
public interface OngoingMatchMapper {

    @Mapping(target = "ended" , source = "matchState.ended")
    @Mapping(target = "inTiebreak" , source = "matchState.inTiebreak")
    @Mapping(target = "sets", source = "score.sets")
    @Mapping(target = "currentPoints", source = "score.currentPoints")
    OngoingMatchResponseDto toDto(OngoingMatch ongoingMatch);



}
