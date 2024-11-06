package org.mrshoffen.dto.response.pageable;


import lombok.Data;

@Data
public final class MatchResponseDto {

    private Integer id;
    private String firstPlayer;
    private String secondPlayer;
    private String winner;
}
