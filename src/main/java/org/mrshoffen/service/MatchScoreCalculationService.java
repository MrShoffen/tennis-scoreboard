package org.mrshoffen.service;

import org.mrshoffen.entity.OngoingMatch;

public class MatchScoreCalculationService {


    public void updateMatchScore(OngoingMatch match, String pointWinner) {

        String opponent = match.getFirstPlayer().getName().equals(pointWinner) ?
                match.getSecondPlayer().getName() : match.getFirstPlayer().getName();

        match.scorePoint(pointWinner);

        if (match.isInTiebreak()) {
            checkForTiebreakWin(match, pointWinner, opponent);
        } else {
            checkForGameWin(match, pointWinner, opponent);
        }

        checkForSetWinOrTiebreak(match, pointWinner, opponent);

        checkIsMatchOver(match, pointWinner);
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
        for (int i = 1; i < match.getCurrentSet(); i++) {
            if (match.isSetWined(pointWinner, i)) {
                wonSets++;
            }
        }
        return wonSets;
    }

    private void checkForSetWinOrTiebreak(OngoingMatch match, String pointWinner, String opponent) {
        if (match.currentGamesWon(pointWinner) == 7) {
            match.startNextSet();
        }
        if (match.getCurrentSet() <= 3
                && match.currentGamesWon(pointWinner) == 6
                && match.currentGamesWon(opponent) == 6) {
            match.setInTiebreak(true);
        }
    }


    private void checkForTiebreakWin(OngoingMatch match, String pointWinner, String opponent) {
        if (match.currentPoints(pointWinner) == 7 && match.currentPoints(opponent) < 6 ||
                match.currentPoints(pointWinner) > 7 && (match.currentPoints(pointWinner) - match.currentPoints(opponent)) == 2) {
            match.scoreGameInSet(pointWinner);
            match.startNextGame();
            match.setInTiebreak(false);
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
