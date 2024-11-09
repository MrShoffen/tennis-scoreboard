package org.mrshoffen.entity.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OngoingMatchScore {

    private int currentSet = 1;

    private Map<Integer, Integer[]> sets = new HashMap<>(2);
    private Map<Integer, Integer> currentPoints = new HashMap<>(2);

    private int pointWinnerNo;
    private int pointLoserNo;

    public OngoingMatchScore() {
        sets.put(1, new Integer[]{0, 0, 0});
        sets.put(2, new Integer[]{0, 0, 0});

        currentPoints.put(1, 0);
        currentPoints.put(2, 0);

    }


    public void scoreGameInCurrentSet() {
        sets.get(pointWinnerNo)[currentSet - 1]++;
    }

    public void startNextGame() {
        currentPoints.put(1, 0);
        currentPoints.put(2, 0);
    }

    public void startNextSet() {
        currentSet++;
    }


    public Integer currentPoints(int playerNo) {
        return currentPoints.get(playerNo);
    }

    public Integer gamesInCurrentSetWon(int playerNo) {
        return sets.get(playerNo)[currentSet - 1];
    }

    public boolean isSetWon(int setNumber) {
        return sets.get(pointWinnerNo)[setNumber - 1] >= 6;
    }

    public void scorePoint(int pointWinner) {
        this.pointWinnerNo = pointWinner;
        this.pointLoserNo = pointWinner == 1 ? 2 : 1;

        currentPoints.put(pointWinner, currentPoints.get(pointWinner) + 1);

    }
}
