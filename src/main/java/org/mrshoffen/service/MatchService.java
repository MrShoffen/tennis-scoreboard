package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.repository.MatchRepository;

public class MatchService {

    private final MatchRepository matchRepository;

    @Inject
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }




}
