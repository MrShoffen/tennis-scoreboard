package org.mrshoffen.dto.response.pageable;


import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Data
public final class MatchResponseDto {

    private Integer id;
    private String firstPlayer;
    private String secondPlayer;
    private String winner;
}
