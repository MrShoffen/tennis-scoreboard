package org.mrshoffen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.OngoingMatch;
import org.mrshoffen.entity.Player;

@Mapper
public interface OngoingMatchMapper {

    @Mapping(target = "firstPlayer", source = "firstPlayer.name")
    @Mapping(target = "secondPlayer", source = "secondPlayer.name")
    OngoingMatchResponseDto toDto(OngoingMatch ongoingMatch);


    public static void main(String[] args) {
        OngoingMatchMapper mapper = Mappers.getMapper(OngoingMatchMapper.class);


        Player first = Player.builder().name("first").build();
        Player second = Player.builder().name("second").build();
        OngoingMatch match = new OngoingMatch(first,second);

        match.scorePoint("first");

        match.scoreGameInCurrentSet("second");
        match.startNextSet();
        match.scoreGameInCurrentSet("first");

        var dto = mapper.toDto(match);



        System.out.println(match);
        System.out.println(dto);

    }




}
