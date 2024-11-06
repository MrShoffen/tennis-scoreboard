package org.mrshoffen.dto.response.score;


import lombok.Data;

@Data
public class OngoingMatchResponseDto {

    private String firstPlayer;
    private String secondPlayer;

    private Integer[] firstPlayerSets;
    private Integer[] secondPlayerSets;

    private String firstPlayerCurrentPoints;
    private String secondPlayerCurrentPoints;

    private boolean ended;

    private boolean inTiebreak;

}
