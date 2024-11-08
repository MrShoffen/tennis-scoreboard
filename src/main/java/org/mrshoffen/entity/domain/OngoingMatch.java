package org.mrshoffen.entity.domain;

import lombok.Data;

@Data
public class OngoingMatch {

    private final String firstPlayer;
    private final String secondPlayer;
    private String winner = null;

    private OngoingMatchScore score;
    private OngoingMatchState matchState;

    public OngoingMatch(String firstPlayer, String secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        score  = new OngoingMatchScore();
        matchState = new OngoingMatchState(score);
    }


    public void scorePoint(String pointWinner) {
        int pointWinnerNo = pointWinner.equals(firstPlayer) ? 1 : 2;

        score.scorePoint(pointWinnerNo);

        handleGameEnd();

        handleSetEnd();

        if (matchState.isWonByPointWinner()) {
            matchState.setEnded(true);
            winner = pointWinner;
            return;
        }

        checkForTiebreak();
    }


    private void handleGameEnd() {
        if (matchState.isInTiebreak()) {
            handleTiebreakEnd();
        } else {
            handleRegularGameEnd();
        }
    }

    private void handleSetEnd() {
        if (matchState.setEnded()) {
            score.startNextSet();
        }
    }


    private void handleRegularGameEnd() {
        if (matchState.regularGameEnded()) {
            score.scoreGameInCurrentSet();
            score.startNextGame();
        }
    }

    private void handleTiebreakEnd() {
        if (matchState.tiebreakEnded()) {
            score.scoreGameInCurrentSet();
            score.startNextGame();
            matchState.setInTiebreak(false);
        }
    }

    private void checkForTiebreak() {
        if (matchState.tiebreak()) {
            matchState.setInTiebreak(true);
        }
    }

}