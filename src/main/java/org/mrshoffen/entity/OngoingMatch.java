package org.mrshoffen.entity;


import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
public class OngoingMatch {

    private final Player firstPlayer;
    private final Player secondPlayer;

    private Player winner = null;

    private int currentSet = 0;

    private Integer[] firstPlayerSets = new Integer[]{0, 0, 0};
    private Integer[] secondPlayerSets = new Integer[]{0, 0, 0};

    private Integer firstPlayerCurrentPoints = 0;
    private Integer secondPlayerCurrentPoints = 0;

    private boolean ended = false;


    public void scorePoint(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            firstPlayerCurrentPoints++;
        } else {
            secondPlayerCurrentPoints++;
        }
    }

    public boolean isOver(){
        return winner != null;
    }

    public Integer currentPoints(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            return firstPlayerCurrentPoints;
        } else {
            return secondPlayerCurrentPoints;
        }
    }

    public Integer currentGamesWinned(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            return firstPlayerSets[currentSet];
        } else {
            return secondPlayerSets[currentSet];
        }
    }

    public boolean isSetWined(String playerName, int setNumber) {
        if (playerName.equals(firstPlayer.getName())) {
            return firstPlayerSets[setNumber-1] == 7;
        } else {
            return secondPlayerSets[setNumber-1] == 7;
        }
    }


    public void clearPoints() {
        firstPlayerCurrentPoints = 0;
        secondPlayerCurrentPoints = 0;
    }

    public void scoreGameInSet(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            firstPlayerSets[currentSet]++;
        } else {
            secondPlayerSets[currentSet]++;
        }
    }

    public void startNextSet() {
        currentSet++;
    }

    public void startNextGame() {
        clearPoints();
    }

    public void wonBy(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            winner = firstPlayer;
        } else {
            winner = secondPlayer;
        }

        ended = true;
    }
}
