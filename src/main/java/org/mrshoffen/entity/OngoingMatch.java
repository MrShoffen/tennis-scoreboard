package org.mrshoffen.entity;


import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

@Data
public class OngoingMatch {

    private final Player firstPlayer;
    private final Player secondPlayer;

    private int currentSet = 0;

    private Integer[] firstPlayerSets = new Integer[]{0, 0, 0};
    private Integer[] secondPlayerSets = new Integer[]{0, 0, 0};

    private Integer firstPlayerCurrentPoints = 0;
    private Integer secondPlayerCurrentPoints = 0;


    public void scorePoint(String playerName) {
        if (playerName.equals(firstPlayer.getName())) {
            firstPlayerCurrentPoints++;
        } else {
            secondPlayerCurrentPoints++;
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
}
