package org.mrshoffen.entity.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OngoingMatchTest {

    private OngoingMatch match;
    private OngoingMatchScore score;
    private OngoingMatchState state;

    private static final String FIRST_PLAYER_NAME = "first";
    private static final String SECOND_PLAYER_NAME = "second";

    void scorePointsToPlayer(PlayerNumber player, int points, OngoingMatch match) {
        for (int i = 0; i < points; i++) {
            match.scorePointToPlayer(player);
        }
    }

    void scoreGamesToPlayer(PlayerNumber player, int games, OngoingMatch match) {
        for (int i = 0; i < games; i++) {
            scorePointsToPlayer(player, 4, match);
        }
    }

    @BeforeEach
    void initialize() {
        match = new OngoingMatch(FIRST_PLAYER_NAME, SECOND_PLAYER_NAME);
        score = match.getScore();
        state = match.getMatchState();
    }

    @Test
    void score4PointsToFirstPlayer_ShouldWinGame() {
        scorePointsToPlayer(PlayerNumber.ONE, 4, match);

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.ONE, 1))
                .isEqualTo(1);

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.TWO, 1))
                .isZero();
    }

    @Test
    void score3PointsToBothPlayers_nextPointShouldNotWinGame() {
        scorePointsToPlayer(PlayerNumber.ONE, 3, match);
        scorePointsToPlayer(PlayerNumber.TWO, 3, match);

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.ONE, 1))
                .isZero();

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.TWO, 1))
                .isZero();

        //add point - game still in progress
        scorePointsToPlayer(PlayerNumber.ONE, 1, match);

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.ONE, 1))
                .isZero();

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.TWO, 1))
                .isZero();
    }

    @Test
    void score3PointsToBothPlayers_ScoreNext2PointsToFirstPlayer_ShouldWinGame() {
        scorePointsToPlayer(PlayerNumber.ONE, 3, match);
        scorePointsToPlayer(PlayerNumber.TWO, 3, match);

        //add another 2 points - game is won after Advantage
        scorePointsToPlayer(PlayerNumber.ONE, 2, match);

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.ONE, 1))
                .isEqualTo(1);

        assertThat(score.gamesWonInSetByPlayer(PlayerNumber.TWO, 1))
                .isZero();
    }

    @Test
    void score6GamesToFirstPlayer_ShouldStartNewSet_ShouldWinSet() {
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);


        assertThat(score.getCurrentSet())
                .isEqualTo(2);

        assertThat(state.setWonByPlayer(PlayerNumber.ONE,1))
                .isTrue();

    }

    @Test
    void score5GamesToSecondAnd6GamesToFirst_ShouldNotStartNewSet_ShouldNotWinSet() {
        scoreGamesToPlayer(PlayerNumber.TWO, 5, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);

        assertThat(score.getCurrentSet())
                .isEqualTo(1);

        assertThat(state.setWonByPlayer(PlayerNumber.ONE,1))
                .isFalse();
    }

    @Test
    void score5GamesToSecondAnd6GamesToFirst_ScoreNextGameToFirst_ShouldStartNewSet_ShouldWinSet() {
        scoreGamesToPlayer(PlayerNumber.TWO, 5, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);

        //score another game to first
        scoreGamesToPlayer(PlayerNumber.ONE, 1, match);

        assertThat(score.getCurrentSet())
                .isEqualTo(2);

        assertThat(state.setWonByPlayer(PlayerNumber.ONE,1))
                .isTrue();
    }

    @Test
    void score5GamesToSecondAnd6GamesToFirst_ScoreNextGameToSecond_ShouldNotStartNewSet_ShouldStartTiebreak() {
        scoreGamesToPlayer(PlayerNumber.TWO, 5, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);

        //score another game to second
        scoreGamesToPlayer(PlayerNumber.TWO, 1, match);

        assertThat(score.getCurrentSet())
                .isEqualTo(1);

        assertThat(state.setWonByPlayer(PlayerNumber.ONE,1))
                .isFalse();
        assertThat(state.setWonByPlayer(PlayerNumber.TWO,1))
                .isFalse();

        assertThat(state.isInTiebreak())
                .isTrue();
    }

    @Test
    void tiebreakSituation_Score7PointsToFirst_ShouldStartNewSet_ShouldEndTiebreak_ShouldWinSet() {
        scoreGamesToPlayer(PlayerNumber.TWO, 5, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);
        scoreGamesToPlayer(PlayerNumber.TWO, 1, match);

        assertThat(state.isInTiebreak())
                .isTrue();

        //score 7 points to first
        scorePointsToPlayer(PlayerNumber.ONE, 7, match);

        assertThat(score.getCurrentSet())
                .isEqualTo(2);

        assertThat(state.isInTiebreak())
                .isFalse();

        assertThat(state.setWonByPlayer(PlayerNumber.ONE,1))
                .isTrue();
    }

    @Test
    void tiebreakSituation_Score6PointsToFirstAnd7PointsToSecond_ShouldNotStartNewSet_ShouldNotEndTiebreak() {
        scoreGamesToPlayer(PlayerNumber.TWO, 5, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);
        scoreGamesToPlayer(PlayerNumber.TWO, 1, match);

        assertThat(state.isInTiebreak())
                .isTrue();

        //score 6 points to first and 7 to second
        scorePointsToPlayer(PlayerNumber.ONE, 6, match);
        scorePointsToPlayer(PlayerNumber.TWO, 7, match);


        assertThat(score.getCurrentSet())
                .isEqualTo(1);

        assertThat(state.isInTiebreak())
                .isTrue();
    }

    @Test
    void tiebreakSituation_Score6PointsToFirstAnd8PointsToSecond_ShouldStartNewSet_ShouldEndTiebreak_ShouldWinSet() {
        scoreGamesToPlayer(PlayerNumber.TWO, 5, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);
        scoreGamesToPlayer(PlayerNumber.TWO, 1, match);

        assertThat(state.isInTiebreak())
                .isTrue();

        //score 6 points to first and 8 to second
        scorePointsToPlayer(PlayerNumber.ONE, 6, match);
        scorePointsToPlayer(PlayerNumber.TWO, 8, match);


        assertThat(score.getCurrentSet())
                .isEqualTo(2);

        assertThat(state.isInTiebreak())
                .isFalse();

        assertThat(state.setWonByPlayer(PlayerNumber.TWO,1))
                .isTrue();
    }

    @Test
    void score2SetsToFirst_ShouldWinMatch(){
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);

        assertThat(state.isEnded())
                .isTrue();

        assertThat(match.getWinner())
                .isEqualTo(FIRST_PLAYER_NAME);
    }

    @Test
    void score1SetToFirstAnd1SetToSecond_ShouldNotWinMatch(){
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);
        scoreGamesToPlayer(PlayerNumber.TWO, 6, match);

        assertThat(state.isEnded())
                .isFalse();
    }

    @Test
    void score1SetToSecondAnd2SetsToFirst_ShouldWinMatch(){
        scoreGamesToPlayer(PlayerNumber.TWO, 6, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);
        scoreGamesToPlayer(PlayerNumber.ONE, 6, match);

        assertThat(state.isEnded())
                .isTrue();

        assertThat(match.getWinner())
                .isEqualTo(FIRST_PLAYER_NAME);
    }

}