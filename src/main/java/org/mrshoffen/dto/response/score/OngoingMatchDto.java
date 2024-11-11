package org.mrshoffen.dto.response.score;


import lombok.Data;
import org.mrshoffen.entity.domain.PlayerNumber;

import java.util.Map;

@Data
public class OngoingMatchDto {

    private String firstPlayer;
    private String secondPlayer;
    private String winner;

    private boolean ended;

    private boolean inTiebreak;

    private Map<PlayerNumber, Integer[]> sets;
    private Map<PlayerNumber, Integer> currentPoints;

}
