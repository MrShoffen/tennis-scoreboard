package org.mrshoffen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mrshoffen.dto.response.pageable.PlayerResponseDto;
import org.mrshoffen.entity.Player;

@Mapper
public interface PlayerMapper {

    @Mapping(target = "matchesPlayed", ignore = true)
    PlayerResponseDto toDto(Player player);

}
