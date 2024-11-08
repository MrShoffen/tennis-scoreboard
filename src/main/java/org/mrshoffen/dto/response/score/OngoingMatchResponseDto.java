package org.mrshoffen.dto.response.score;


import lombok.Data;

import java.util.Map;

@Data
public class OngoingMatchResponseDto {

    private String firstPlayer;
    private String secondPlayer;
    private String winner;

    private boolean ended;

    private boolean inTiebreak;

    private Map<Integer, Integer[]> sets;
    private Map<Integer, Integer> currentPoints;

}
