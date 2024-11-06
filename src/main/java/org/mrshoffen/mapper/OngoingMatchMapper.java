package org.mrshoffen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.domain.OngoingMatch;

@Mapper
public interface OngoingMatchMapper {

    @Mapping(target = "ended" , source = "state.ended")
    @Mapping(target = "inTiebreak" , source = "state.inTiebreak")
    OngoingMatchResponseDto toDto(OngoingMatch ongoingMatch);

}
