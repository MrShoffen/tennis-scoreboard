package org.mrshoffen.entity.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OngoingMatchScore {

    private int currentSet = 1;

    private Map<PlayerNumber, Integer[]> sets = new HashMap<>(2);
    private Map<PlayerNumber, Integer> currentPoints = new HashMap<>(2);


    public OngoingMatchScore() {
        sets.put(PlayerNumber.ONE, new Integer[]{0, 0, 0});
        sets.put(PlayerNumber.TWO, new Integer[]{0, 0, 0});

        currentPoints.put(PlayerNumber.ONE, 0);
        currentPoints.put(PlayerNumber.TWO, 0);
    }


    public void scorePointToPlayer(PlayerNumber pointWinner) {
        currentPoints.put(pointWinner, currentPoints.get(pointWinner) + 1);
    }

    public void scoreGameToPlayerInCurrentSet(PlayerNumber playerNo) {
        sets.get(playerNo)[currentSet - 1]++;
    }

    public void startNextGame() {
        currentPoints.put(PlayerNumber.ONE, 0);
        currentPoints.put(PlayerNumber.TWO, 0);
    }

    public void startNextSet() {
        currentSet++;
    }

    public Integer pointsWonInCurrentGame(PlayerNumber playerNo) {
        return currentPoints.get(playerNo);
    }

    public Integer gamesWonInSetByPlayer(PlayerNumber playerNo,int setNumber) {
        return sets.get(playerNo)[setNumber - 1];
    }
}
