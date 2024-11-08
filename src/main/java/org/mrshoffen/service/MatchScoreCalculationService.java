package org.mrshoffen.service;

import org.mrshoffen.entity.domain.OngoingMatch;

public class MatchScoreCalculationService {

    public void updateMatchScore(OngoingMatch match, String pointWinner) {

        match.scorePoint(pointWinner);


    }
}
