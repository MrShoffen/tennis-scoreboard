package org.mrshoffen.entity.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OngoingMatchState {
    private static final int SETS_FOR_MATCH_WIN = 2;
    private static final int MAX_SETS_IN_MATCH = 3;

    private static final int GAMES_FOR_SET_WIN = 6;
    private static final int GAMES_DIF_FOR_SET_WIN = 2;
    private static final int GAMES_FOR_TIEBREAK_START = 6;

    private static final int POINTS_FOR_TIEBREAK_WIN = 7;
    private static final int POINTS_DIF_FOR_TIEBREAK_WIN = 2;

    private static final int POINTS_FOR_REGULAR_GAME_WIN = 4;
    private static final int POINTS_DIF_FOR_REGULAR_GAME_WIN = 2;


    private final OngoingMatchScore score;

    private boolean ended = false;
    private boolean inTiebreak = false;

    public OngoingMatchState(OngoingMatchScore score) {
        this.score = score;
    }


    public boolean tiebreak() {
        return score.getCurrentSet() <= MAX_SETS_IN_MATCH
                && score.gamesInCurrentSetWon(1) == GAMES_FOR_TIEBREAK_START
                && score.gamesInCurrentSetWon(2) == GAMES_FOR_TIEBREAK_START;
    }


    public boolean tiebreakEnded() {
        return tiebreakWonClear() || tiebreakWonAfterAdditionalRounds();
    }

    public boolean regularGameEnded() {
        return gameWonClear() || gameWonAfterAdditionalRounds();
    }

    public boolean setEnded() {
        return setWonClear() || setWonAfterTiebreak();
    }


    public boolean isWonByPointWinner() {
        int wonSets = 0;
        for (int i = 1; i < score.getCurrentSet(); i++) {
            if (score.isSetWon(i)) {
                wonSets++;
            }
        }

        return wonSets == SETS_FOR_MATCH_WIN;
    }



    public boolean setWonClear() {
        return score.gamesInCurrentSetWon(score.getPointWinner()) == GAMES_FOR_SET_WIN
                && (score.gamesInCurrentSetWon(score.getPointWinner())  - score.gamesInCurrentSetWon(score.getPointLoser()) >= GAMES_DIF_FOR_SET_WIN);
    }

    private boolean setWonAfterTiebreak() {
        return score.gamesInCurrentSetWon(score.getPointWinner()) > GAMES_FOR_TIEBREAK_START
                && (score.gamesInCurrentSetWon(score.getPointWinner())  - score.gamesInCurrentSetWon(score.getPointLoser()) == 1);

    }


    private boolean tiebreakWonClear() {
        return score.currentPoints(score.getPointWinner()) == POINTS_FOR_TIEBREAK_WIN
                && (score.currentPoints(score.getPointWinner()) - score.currentPoints(score.getPointLoser())) >= POINTS_DIF_FOR_TIEBREAK_WIN;
    }

    private boolean tiebreakWonAfterAdditionalRounds() {
        return score.currentPoints(score.getPointWinner()) > POINTS_FOR_TIEBREAK_WIN
                && (score.currentPoints(score.getPointWinner()) - score.currentPoints(score.getPointLoser())) == POINTS_DIF_FOR_TIEBREAK_WIN;
    }


    private boolean gameWonClear() {
        return score.currentPoints(score.getPointWinner()) == POINTS_FOR_REGULAR_GAME_WIN
                && (score.currentPoints(score.getPointWinner()) - score.currentPoints(score.getPointLoser())) >= POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

    private boolean gameWonAfterAdditionalRounds() {
        return score.currentPoints(score.getPointWinner()) > POINTS_FOR_REGULAR_GAME_WIN
                && (score.currentPoints(score.getPointWinner()) - score.currentPoints(score.getPointLoser())) == POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

}
