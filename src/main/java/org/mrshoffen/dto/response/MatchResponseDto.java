package org.mrshoffen.dto.response;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public final class MatchResponseDto extends EntityResponseDto {

    private String firstPlayer;
    private String secondPlayer;
    private String winner;
}
