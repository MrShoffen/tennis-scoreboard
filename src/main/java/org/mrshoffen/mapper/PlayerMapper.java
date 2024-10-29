package org.mrshoffen.mapper;

import org.mapstruct.Mapper;
import org.mrshoffen.dto.response.PlayerResponseDto;
import org.mrshoffen.entity.Player;

@Mapper
public interface PlayerMapper {

    PlayerResponseDto toDto(Player player);

}
