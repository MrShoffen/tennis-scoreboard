package org.mrshoffen.service;

import org.mrshoffen.entity.domain.OngoingMatch;

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
        if (match.getState().isInTiebreak()) {
            handleTiebreakEnd(match);
        } else {
            handleRegularGameEnd(match);
        }
    }

    private void handleSetEnd(OngoingMatch match) {
        if (match.getState().currentSetEnded()) {
            match.startNextSet();
        }
    }

    private void handleMatchEnd(OngoingMatch match) {
        if (match.getState().isWonByPointWinner()) {
            match.setPointWinnerAsMatchWinner();
            match.getState().setEnded(true);
        }
    }

    private void handleRegularGameEnd(OngoingMatch match) {
        if (match.getState().regularGameEnded()) {
            match.scoreGameInCurrentSet();
            match.startNextGame();
        }
    }

    private void handleTiebreakEnd(OngoingMatch match) {
        if (match.getState().tiebreakEnded()) {
            match.scoreGameInCurrentSet();
            match.startNextGame();
            match.getState().setInTiebreak(false);
        }
    }

    private void checkForTiebreak(OngoingMatch match) {
        if (match.getState().setInTiebreak()) {
            match.getState().setInTiebreak(true);
        }
    }
}
