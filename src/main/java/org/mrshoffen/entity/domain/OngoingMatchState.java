package org.mrshoffen.entity.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OngoingMatchState {
    private static final int SETS_FOR_MATCH_WIN = 2;
    private static final int MAX_SETS_IN_MATCH = 3;

    private static final int GAMES_FOR_SET_WIN = 7;
    private static final int GAMES_FOR_TIEBREAK_START = 6;

    private static final int POINTS_FOR_TIEBREAK_WIN = 7;
    private static final int POINTS_DIF_FOR_TIEBREAK_WIN = 2;

    private static final int POINTS_FOR_REGULAR_GAME_WIN = 4;
    private static final int POINTS_DIF_FOR_REGULAR_GAME_WIN = 2;

    private final OngoingMatch match;

    private boolean ended = false;
    private boolean inTiebreak = false;

    public OngoingMatchState(OngoingMatch match) {
        this.match = match;
    }


    public boolean setInTiebreak() {
        return match.getCurrentSet() <= MAX_SETS_IN_MATCH
                && match.gamesInCurrentSetWon(match.getFirstPlayer()) == GAMES_FOR_TIEBREAK_START
                && match.gamesInCurrentSetWon(match.getSecondPlayer()) == GAMES_FOR_TIEBREAK_START;
    }


    public boolean tiebreakEnded() {
        return tiebreakWonWithNoAd() || tiebreakWonWithAd();
    }

    public boolean regularGameEnded() {
        return gameWonWithNoAd() || gameWonWithAd();
    }


    public boolean isWonByPointWinner() {
        return numberOfWonSets() == SETS_FOR_MATCH_WIN;
    }

    public int numberOfWonSets() {
        int wonSets = 0;
        for (int i = 1; i < match.getCurrentSet(); i++) {
            if (match.isSetWon(i)) {
                wonSets++;
            }
        }
        return wonSets;
    }

    public boolean currentSetEnded() {
        return match.gamesInCurrentSetWon(match.getPointWinner()) == GAMES_FOR_SET_WIN;
    }


    private boolean tiebreakWonWithNoAd() {
        return match.currentPoints(match.getPointWinner()) == POINTS_FOR_TIEBREAK_WIN
                && (match.currentPoints(match.getPointWinner()) - match.currentPoints(match.getPointLoser())) >= POINTS_DIF_FOR_TIEBREAK_WIN;
    }

    private boolean tiebreakWonWithAd() {
        return match.currentPoints(match.getPointWinner()) > POINTS_FOR_TIEBREAK_WIN
                && (match.currentPoints(match.getPointWinner()) - match.currentPoints(match.getPointLoser())) == POINTS_DIF_FOR_TIEBREAK_WIN;
    }


    private boolean gameWonWithNoAd() {
        return match.currentPoints(match.getPointWinner()) == POINTS_FOR_REGULAR_GAME_WIN
                && (match.currentPoints(match.getPointWinner()) - match.currentPoints(match.getPointLoser())) >= POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

    private boolean gameWonWithAd() {
        return match.currentPoints(match.getPointWinner()) > POINTS_FOR_REGULAR_GAME_WIN
                && (match.currentPoints(match.getPointWinner()) - match.currentPoints(match.getPointLoser())) == POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

}
