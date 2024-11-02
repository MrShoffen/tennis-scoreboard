package org.mrshoffen.service;

import org.mrshoffen.entity.OngoingMatch;

public class MatchScoreCalculationService {


    public void updateMatchScore(OngoingMatch match, String pointWinner) {

        String opponent = match.getFirstPlayer().getName().equals(pointWinner) ?
                match.getSecondPlayer().getName() : match.getFirstPlayer().getName();

        match.scorePoint(pointWinner);


        if (isMatchInTieBreak(match, pointWinner, opponent)) {
            handleTieBreak(match, pointWinner, opponent);
        } else {
            checkForGameWin(match, pointWinner, opponent);
            checkForSetWin(match, pointWinner, opponent);
        }

        checkIsMatchOver(match, pointWinner);

        System.out.println();

    }

    private void checkIsMatchOver(OngoingMatch match, String pointWinner) {
        if (match.getCurrentSet() >= 2) {
            if (countWonSets(match, pointWinner) == 2) {
                match.wonBy(pointWinner);
            }
        }
    }

    private int countWonSets(OngoingMatch match, String pointWinner) {
        int wonSets = 0;
        for (int i = 1; i <= match.getCurrentSet(); i++) {
            if (match.isSetWined(pointWinner, i)) {
                wonSets++;
            }
        }
        return wonSets;
    }

    private void handleTieBreak(OngoingMatch match, String pointWinner, String opponent) {
        if (match.currentPoints(pointWinner) == 7 && match.currentPoints(opponent) < 6 ||
                match.currentPoints(pointWinner) > 7 && (match.currentPoints(pointWinner) - match.currentPoints(opponent)) == 2) {
            match.scoreGameInSet(pointWinner);
            match.startNextGame();
            match.startNextSet();
        }
    }

    private boolean isMatchInTieBreak(OngoingMatch match, String pointWinner, String opponent) {
        return match.currentGamesWinned(pointWinner) == 6 && match.currentGamesWinned(opponent) == 6;
    }

    private void checkForSetWin(OngoingMatch match, String pointWinner, String opponent) {
        if (match.currentGamesWinned(pointWinner) == 7 && match.currentGamesWinned(opponent) < 6) {
            match.startNextSet();
        }
    }

    private void checkForGameWin(OngoingMatch match, String pointWinner, String opponent) {
        if (match.currentPoints(pointWinner) == 4 && match.currentPoints(opponent) < 3 ||
                match.currentPoints(pointWinner) > 4 && (match.currentPoints(pointWinner) - match.currentPoints(opponent)) == 2) {
            match.scoreGameInSet(pointWinner);
            match.startNextGame();
        }
    }

}
