package org.mrshoffen.entity;


import lombok.Data;

@Data
public class OngoingMatch {

    private final Player firstPlayer;
    private final Player secondPlayer;

    private Player winner = null;

    private int currentSet = 1;

    private Integer[] firstPlayerSets = new Integer[]{0, 0, 0};
    private Integer[] secondPlayerSets = new Integer[]{0, 0, 0};

    private Integer firstPlayerCurrentPoints = 0;
    private Integer secondPlayerCurrentPoints = 0;

    private boolean ended = false;

    private boolean inTiebreak = false;


    public void scorePoint(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            firstPlayerCurrentPoints++;
        } else {
            secondPlayerCurrentPoints++;
        }
    }

    public Integer currentPoints(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            return firstPlayerCurrentPoints;
        } else {
            return secondPlayerCurrentPoints;
        }
    }

    public Integer currentGamesWon(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            return firstPlayerSets[currentSet-1];
        } else {
            return secondPlayerSets[currentSet-1];
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

    public void scoreGameInCurrentSet(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            firstPlayerSets[currentSet-1]++;
        } else {
            secondPlayerSets[currentSet-1]++;
        }
    }

    public void startNextSet() {
        currentSet++;
    }

    public void startNextGame() {
        clearPoints();
    }

    public void setWinner(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            winner = firstPlayer;
        } else {
            winner = secondPlayer;
        }
    }
}
