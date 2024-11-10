package org.mrshoffen.entity.domain;

import lombok.Data;
import org.mrshoffen.entity.persistence.Player;
import org.mrshoffen.exception.ValidationException;

import java.time.LocalDateTime;

@Data
public class OngoingMatch {

    private final String firstPlayer;
    private final String secondPlayer;
    private String winner = null;

    private OngoingMatchScore score;
    private OngoingMatchState matchState;

    private LocalDateTime createdTime;

    public OngoingMatch(String firstPlayer, String secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        score  = new OngoingMatchScore();
        matchState = new OngoingMatchState(score);

        this.createdTime = LocalDateTime.now();
    }




    public void scorePointToPlayer(PlayerNumber pointWinnerNo) {


        score.scorePointToPlayer(pointWinnerNo);

        handleGameEnd(pointWinnerNo);

        handleSetEnd(pointWinnerNo);

        if (matchState.isMatchWonByPointWinner(pointWinnerNo)) {
            matchState.setEnded(true);
            winner = pointWinnerNo == PlayerNumber.ONE ? firstPlayer : secondPlayer;
            return;
        }

        checkForTiebreak();
    }


    private void handleGameEnd(PlayerNumber pointWinnerNo) {
        if (matchState.isInTiebreak()) {
            handleTiebreakEnd(pointWinnerNo);
        } else {
            handleRegularGameEnd(pointWinnerNo);
        }
    }

    private void handleSetEnd(PlayerNumber pointWinnerNo) {
        if (matchState.setWonByPointWinner(pointWinnerNo)) {
            score.startNextSet();
        }
    }


    private void handleRegularGameEnd(PlayerNumber pointWinnerNo) {
        if (matchState.regularGameWonByPointWinner(pointWinnerNo)) {
            score.scoreGameToPlayerInCurrentSet(pointWinnerNo);
            score.startNextGame();
        }
    }

    private void handleTiebreakEnd(PlayerNumber pointWinnerNo) {
        if (matchState.tiebreakWonByPointWinner(pointWinnerNo)) {
            score.scoreGameToPlayerInCurrentSet(pointWinnerNo);
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