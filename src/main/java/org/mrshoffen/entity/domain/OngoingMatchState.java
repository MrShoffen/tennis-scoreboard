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
                && score.gamesWonInSetByPlayer(PlayerNumber.ONE, score.getCurrentSet()) == GAMES_FOR_TIEBREAK_START
                && score.gamesWonInSetByPlayer(PlayerNumber.TWO, score.getCurrentSet()) == GAMES_FOR_TIEBREAK_START;
    }


    public boolean tiebreakWonByPointWinner(PlayerNumber pointWinnerNo) {
        return tiebreakWonClear(pointWinnerNo) || tiebreakWonAfterAdditionalRounds(pointWinnerNo);
    }

    public boolean regularGameWonByPointWinner(PlayerNumber pointWinnerNo) {
        return gameWonClear(pointWinnerNo) || gameWonAfterAdditionalRounds(pointWinnerNo);
    }

    public boolean setWonByPointWinner(PlayerNumber pointWinnerNo) {
        return setWonByPlayer(pointWinnerNo, score.getCurrentSet());
    }


    public boolean isMatchWonByPointWinner(PlayerNumber pointWinnerNo) {
        int wonSets = 0;
        for (int i = 1; i < score.getCurrentSet(); i++) {
            if (setWonByPlayer(pointWinnerNo, i)) {
                wonSets++;
            }
        }

        return wonSets == SETS_FOR_MATCH_WIN;
    }

    public boolean setWonByPlayer(PlayerNumber playerNo, int setNumber) {
        return setWonClear(playerNo, setNumber) || setWonAfterTiebreak(playerNo, setNumber);
    }


    private boolean setWonClear(PlayerNumber pointWinnerNo, int setNumber) {
        return score.gamesWonInSetByPlayer( pointWinnerNo, setNumber) >= GAMES_FOR_SET_WIN
                && (score.gamesWonInSetByPlayer( pointWinnerNo, setNumber) - score.gamesWonInSetByPlayer( pointWinnerNo.opponent(), setNumber) >= GAMES_DIF_FOR_SET_WIN);
    }

    private boolean setWonAfterTiebreak(PlayerNumber pointWinnerNo, int setNumber) {
        return score.gamesWonInSetByPlayer( pointWinnerNo, setNumber) > GAMES_FOR_TIEBREAK_START
                && (score.gamesWonInSetByPlayer( pointWinnerNo, setNumber) - score.gamesWonInSetByPlayer(pointWinnerNo.opponent(), setNumber) == 1);

    }


    private boolean tiebreakWonClear(PlayerNumber pointWinnerNo) {
        return score.pointsWonInCurrentGame(pointWinnerNo) == POINTS_FOR_TIEBREAK_WIN
                && (score.pointsWonInCurrentGame(pointWinnerNo) - score.pointsWonInCurrentGame(pointWinnerNo.opponent())) >= POINTS_DIF_FOR_TIEBREAK_WIN;
    }

    private boolean tiebreakWonAfterAdditionalRounds(PlayerNumber pointWinnerNo) {
        return score.pointsWonInCurrentGame(pointWinnerNo) > POINTS_FOR_TIEBREAK_WIN
                && (score.pointsWonInCurrentGame(pointWinnerNo) - score.pointsWonInCurrentGame(pointWinnerNo.opponent())) == POINTS_DIF_FOR_TIEBREAK_WIN;
    }


    private boolean gameWonClear(PlayerNumber pointWinnerNo) {
        return score.pointsWonInCurrentGame(pointWinnerNo) == POINTS_FOR_REGULAR_GAME_WIN
                && (score.pointsWonInCurrentGame(pointWinnerNo) - score.pointsWonInCurrentGame(pointWinnerNo.opponent())) >= POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

    private boolean gameWonAfterAdditionalRounds(PlayerNumber pointWinnerNo) {
        return score.pointsWonInCurrentGame(pointWinnerNo) > POINTS_FOR_REGULAR_GAME_WIN
                && (score.pointsWonInCurrentGame(pointWinnerNo) - score.pointsWonInCurrentGame(pointWinnerNo.opponent())) == POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

}
