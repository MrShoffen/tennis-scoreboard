package org.mrshoffen.service;

import org.mrshoffen.entity.OngoingMatch;

public class MatchScoreCalculationService {

    private static final int SETS_FOR_MATCH_WIN = 2;
    private static final int MAX_SETS_IN_MATCH = 3;

    private static final int GAMES_FOR_SET_WIN = 7;
    private static final int GAMES_FOR_TIEBREAK_START = 6;

    private static final int POINTS_FOR_TIEBREAK_WIN = 7;
    private static final int POINTS_DIF_FOR_TIEBREAK_WIN = 2;

    private static final int POINTS_FOR_REGULAR_GAME_WIN = 4;
    private static final int POINTS_DIF_FOR_REGULAR_GAME_WIN = 2;


    public void updateMatchScore(OngoingMatch match, String pointWinner) {

        String opponent = match.getFirstPlayer().getName().equals(pointWinner) ?
                match.getSecondPlayer().getName() : match.getFirstPlayer().getName();

        match.scorePoint(pointWinner);

        if (match.isInTiebreak()) {
            handleTiebreakPoint(match, pointWinner, opponent);
        } else {
            handleRegularPoint(match, pointWinner, opponent);
        }

        checkForSetWin(match, pointWinner);

        checkForTiebreak(match, pointWinner, opponent);

        checkIsMatchOver(match, pointWinner);
    }

    private void checkIsMatchOver(OngoingMatch match, String pointWinner) {
        if (match.getCurrentSet() >= SETS_FOR_MATCH_WIN) {
            if (numberOfWonSets(match, pointWinner) == SETS_FOR_MATCH_WIN) {
                match.setWinner(pointWinner);
                match.setEnded(true);
            }
        }
    }

    private int numberOfWonSets(OngoingMatch match, String pointWinner) {
        int wonSets = 0;
        for (int i = 1; i < match.getCurrentSet(); i++) {
            if (match.isSetWined(pointWinner, i)) {
                wonSets++;
            }
        }
        return wonSets;
    }

    private void checkForSetWin(OngoingMatch match, String pointWinner) {
        if (match.currentGamesWon(pointWinner) == GAMES_FOR_SET_WIN) {
            match.startNextSet();
        }
    }

    private void checkForTiebreak(OngoingMatch match, String pointWinner, String opponent) {
        if (matchInTieBreak(match, pointWinner, opponent)) {
            match.setInTiebreak(true);
        }
    }


    private void handleRegularPoint(OngoingMatch match, String pointWinner, String opponent) {
        if (gameWon(match, pointWinner, opponent)) {
            match.scoreGameInCurrentSet(pointWinner);
            match.startNextGame();
        }
    }

    private void handleTiebreakPoint(OngoingMatch match, String pointWinner, String opponent) {
        if (tiebreakWon(match, pointWinner, opponent)) {
            match.scoreGameInCurrentSet(pointWinner);
            match.startNextGame();
            match.setInTiebreak(false);
        }
    }


    private boolean matchInTieBreak(OngoingMatch match, String pointWinner, String opponent) {
        return match.getCurrentSet() <= MAX_SETS_IN_MATCH
                && match.currentGamesWon(pointWinner) == GAMES_FOR_TIEBREAK_START
                && match.currentGamesWon(opponent) == GAMES_FOR_TIEBREAK_START;
    }


    private boolean tiebreakWon(OngoingMatch match, String pointWinner, String opponent) {
        return tiebreakWonWithNoAd(match, pointWinner, opponent) || tiebreakWonWithAd(match, pointWinner, opponent);
    }

    private boolean tiebreakWonWithNoAd(OngoingMatch match, String pointWinner, String opponent) {
        return match.currentPoints(pointWinner) == POINTS_FOR_TIEBREAK_WIN
                && (match.currentPoints(pointWinner) - match.currentPoints(opponent)) >= POINTS_DIF_FOR_TIEBREAK_WIN;
    }

    private boolean tiebreakWonWithAd(OngoingMatch match, String pointWinner, String opponent) {
        return match.currentPoints(pointWinner) > POINTS_FOR_TIEBREAK_WIN
                && (match.currentPoints(pointWinner) - match.currentPoints(opponent)) == POINTS_DIF_FOR_TIEBREAK_WIN;
    }


    private boolean gameWon(OngoingMatch match, String pointWinner, String opponent) {
        return gameWonWithNoAd(match, pointWinner, opponent) || gameWonWithAd(match, pointWinner, opponent);
    }

    private boolean gameWonWithNoAd(OngoingMatch match, String pointWinner, String opponent) {
        return match.currentPoints(pointWinner) == POINTS_FOR_REGULAR_GAME_WIN
                && (match.currentPoints(pointWinner) - match.currentPoints(opponent)) >= POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

    private boolean gameWonWithAd(OngoingMatch match, String pointWinner, String opponent) {
        return match.currentPoints(pointWinner) > POINTS_FOR_REGULAR_GAME_WIN
                && (match.currentPoints(pointWinner) - match.currentPoints(opponent)) == POINTS_DIF_FOR_REGULAR_GAME_WIN;
    }

}
