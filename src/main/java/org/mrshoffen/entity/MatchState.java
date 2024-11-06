package org.mrshoffen.entity;

public class MatchState {
    private static final int SETS_FOR_MATCH_WIN = 2;
    private static final int MAX_SETS_IN_MATCH = 3;

    private static final int GAMES_FOR_SET_WIN = 7;
    private static final int GAMES_FOR_TIEBREAK_START = 6;

    private static final int POINTS_FOR_TIEBREAK_WIN = 7;
    private static final int POINTS_DIF_FOR_TIEBREAK_WIN = 2;

    private static final int POINTS_FOR_REGULAR_GAME_WIN = 4;
    private static final int POINTS_DIF_FOR_REGULAR_GAME_WIN = 2;

    private final OngoingMatch match;

    public MatchState(OngoingMatch match) {
        this.match = match;
    }


    public boolean inTieBreak() {
        return match.getCurrentSet() <= MAX_SETS_IN_MATCH
                && match.gamesInCurrentSetWon(match.getFirstPlayer()) == GAMES_FOR_TIEBREAK_START
                && match.gamesInCurrentSetWon(match.getSecondPlayer()) == GAMES_FOR_TIEBREAK_START;
    }


}
