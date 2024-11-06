package org.mrshoffen.service;

import org.mrshoffen.entity.OngoingMatch;

public class MatchScoreCalculationService {

    public void updateMatchScore(OngoingMatch match, String pointWinner) {

        match.setPointWinner(pointWinner);
        match.scoreToPointWinner();

        handleGameEnd(match);
        handleSetEnd(match);
        handleMatchEnd(match);

        checkForTiebreak(match);
    }

    private void handleGameEnd(OngoingMatch match) {
        if (match.isInTiebreak()) {
            handleTiebreakEnd(match);
        } else {
            handleRegularGameEnd(match);
        }
    }

    private void handleSetEnd(OngoingMatch match) {
        if (match.currentSetEnded()) {
            match.startNextSet();
        }
    }

    private void handleMatchEnd(OngoingMatch match) {
        if (match.isWonByPointWinner()) {
            match.setPointWinnerAsMatchWinner();
            match.setEnded(true);
        }
    }

    private void handleRegularGameEnd(OngoingMatch match) {
        if (match.gameEnded()) {
            match.scoreGameInCurrentSet();
            match.startNextGame();
        }
    }

    private void handleTiebreakEnd(OngoingMatch match) {
        if (match.tiebreakEnded()) {
            match.scoreGameInCurrentSet();
            match.startNextGame();
            match.setInTiebreak(false);
        }
    }

    private void checkForTiebreak(OngoingMatch match) {
        if (match.inTieBreak()) {
            match.setInTiebreak(true);
        }
    }
}
