package org.mrshoffen.entity;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OngoingMatch {
    private static final int SETS_FOR_MATCH_WIN = 2;
    private static final int MAX_SETS_IN_MATCH = 3;

    private static final int GAMES_FOR_SET_WIN = 7;
    private static final int GAMES_FOR_TIEBREAK_START = 6;

    private static final int POINTS_FOR_TIEBREAK_WIN = 7;
    private static final int POINTS_DIF_FOR_TIEBREAK_WIN = 2;

    private static final int POINTS_FOR_REGULAR_GAME_WIN = 4;
    private static final int POINTS_DIF_FOR_REGULAR_GAME_WIN = 2;


    private final String firstPlayer;
    private final String secondPlayer;
    private String winner = null;

    private String pointWinner;
    private String pointLoser;

    private int currentSet = 1;

    private Map<String, Integer[]> sets = new HashMap<>(2);

    private Map<String, Integer> currentPoints = new HashMap<>(2);

    private boolean ended = false;

    private boolean inTiebreak = false;

    private MatchState state;

    public OngoingMatch(String firstPlayer, String secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        sets.put(firstPlayer, new Integer[]{0, 0, 0});
        sets.put(secondPlayer, new Integer[]{0, 0, 0});

        currentPoints.put(firstPlayer, 0);
        currentPoints.put(secondPlayer, 0);
    }

    public void setPointWinner(String pointWinner) {
        this.pointWinner = firstPlayer.equals(pointWinner) ? firstPlayer : secondPlayer;
        this.pointLoser = firstPlayer.equals(pointWinner) ? secondPlayer : firstPlayer;
    }


    public void scoreToPointWinner() {
       currentPoints.put(pointWinner, currentPoints.get(pointWinner) + 1);
    }

    public void scoreGameInCurrentSet() {
        sets.get(pointWinner)[currentSet-1]++;
    }

    public void startNextSet() {
        currentSet++;
    }

    public void startNextGame() {
        currentPoints.put(firstPlayer, 0);
        currentPoints.put(secondPlayer, 0);
    }

    public void setPointWinnerAsMatchWinner(){
        winner = pointWinner;
    }

    public boolean inTieBreak() {
        return getCurrentSet() <= MAX_SETS_IN_MATCH
                && gamesInCurrentSetWon(firstPlayer) == GAMES_FOR_TIEBREAK_START
                && gamesInCurrentSetWon(secondPlayer) == GAMES_FOR_TIEBREAK_START;
    }


    public boolean tiebreakEnded() {
        return tiebreakWonWithNoAd() || tiebreakWonWithAd();
    }

    public boolean gameEnded() {
        return gameWonWithNoAd() || gameWonWithAd();
    }

    public boolean isWonByPointWinner(){
        return numberOfWonSets() == SETS_FOR_MATCH_WIN;
    }

    public boolean currentSetEnded(){
        return gamesInCurrentSetWon(pointWinner) == GAMES_FOR_SET_WIN;
    }

    private Integer currentPoints(String playerName) {
        return currentPoints.get(playerName);
    }

    public Integer gamesInCurrentSetWon(String playerName) {
        return sets.get(playerName)[currentSet-1];
    }

    private boolean isSetWon(int setNumber) {
        return sets.get(pointWinner)[setNumber-1] == GAMES_FOR_SET_WIN;
    }

    private boolean tiebreakWonWithNoAd() {
        return currentPoints(pointWinner) == POINTS_FOR_TIEBREAK_WIN
                && (currentPoints(pointWinner) - currentPoints(pointLoser)) >= POINTS_DIF_FOR_TIEBREAK_WIN;
    }

    private boolean tiebreakWonWithAd() {
        return currentPoints(pointWinner) > POINTS_FOR_TIEBREAK_WIN
                && (currentPoints(pointWinner) - currentPoints(pointLoser)) == POINTS_DIF_FOR_TIEBREAK_WIN;
    }


    private boolean gameWonWithNoAd() {
        return currentPoints(pointWinner) == POINTS_FOR_REGULAR_GAME_WIN
                && (currentPoints(pointWinner) - currentPoints(pointLoser)) >= POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

    private boolean gameWonWithAd() {
        return currentPoints(pointWinner) > POINTS_FOR_REGULAR_GAME_WIN
                && (currentPoints(pointWinner) - currentPoints(pointLoser)) == POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }


    private int numberOfWonSets() {
        int wonSets = 0;
        for (int i = 1; i < getCurrentSet(); i++) {
            if (isSetWon(i)) {
                wonSets++;
            }
        }
        return wonSets;
    }

}
