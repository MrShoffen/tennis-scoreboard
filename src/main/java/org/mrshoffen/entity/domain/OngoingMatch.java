package org.mrshoffen.entity.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OngoingMatch {

    private final String firstPlayer;
    private final String secondPlayer;
    private String winner = null;

    private Map<String, Integer[]> sets = new HashMap<>(2);
    private Map<String, Integer> currentPoints = new HashMap<>(2);

    private String pointWinner;
    private String pointLoser;

    private int currentSet = 1;

    private OngoingMatchState state;

    public OngoingMatch(String firstPlayer, String secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        sets.put(firstPlayer, new Integer[]{0, 0, 0});
        sets.put(secondPlayer, new Integer[]{0, 0, 0});

        currentPoints.put(firstPlayer, 0);
        currentPoints.put(secondPlayer, 0);

        state = new OngoingMatchState(this);
    }

    public void setPointWinner(String pointWinner) {
        this.pointWinner = firstPlayer.equals(pointWinner) ? firstPlayer : secondPlayer;
        this.pointLoser = firstPlayer.equals(pointWinner) ? secondPlayer : firstPlayer;
    }


    public void scoreToPointWinner() {
        currentPoints.put(pointWinner, currentPoints.get(pointWinner) + 1);
    }

    public void scoreGameInCurrentSet() {
        sets.get(pointWinner)[currentSet - 1]++;
    }

    public void startNextGame() {
        currentPoints.put(firstPlayer, 0);
        currentPoints.put(secondPlayer, 0);
    }

    public void startNextSet() {
        currentSet++;
    }

    public void setPointWinnerAsMatchWinner() {
        winner = pointWinner;
    }

    public Integer currentPoints(String playerName) {
        return currentPoints.get(playerName);
    }

    public Integer gamesInCurrentSetWon(String playerName) {
        return sets.get(playerName)[currentSet - 1];
    }

    public boolean isSetWon(int setNumber) {
        return sets.get(pointWinner)[setNumber - 1] == 7;
    }
}