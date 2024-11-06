package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.dto.request.PointScoreDto;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.domain.OngoingMatch;
import org.mrshoffen.entity.persistence.Match;
import org.mrshoffen.entity.persistence.Player;
import org.mrshoffen.mapper.OngoingMatchMapper;
import org.mrshoffen.repository.MatchRepository;
import org.mrshoffen.repository.PlayerRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class OngoingMatchesService {

    private static final Map<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();

    private final OngoingMatchMapper ongoingMatchMapper;

    private final MatchScoreCalculationService matchScoreCalculationService;

    @Inject
    public OngoingMatchesService( OngoingMatchMapper ongoingMatchMapper, MatchScoreCalculationService matchScoreCalculationService) {
        this.ongoingMatchMapper = ongoingMatchMapper;
        this.matchScoreCalculationService = matchScoreCalculationService;
    }


    public UUID createMatch(NewMatchRequestDto newMatchRequestDto) {

        var firstPlayer = Player.builder()
                .name(newMatchRequestDto.getFirstPlayer())
                .build();

        var secondPlayer = Player.builder()
                .name(newMatchRequestDto.getSecondPlayer())
                .build();

        var match = new OngoingMatch(firstPlayer.getName(), secondPlayer.getName());


        UUID uuid = UUID.randomUUID();

        ongoingMatches.put(uuid, match);

        return uuid;
    }

    public OngoingMatchResponseDto getMatchById(UUID uuid) {
        //todo handle non existing match

        return ongoingMatchMapper.toDto(ongoingMatches.get(uuid));


    }


    public OngoingMatchResponseDto updateMatch(UUID uuid, PointScoreDto pointScoreDto) {

        //todo add validation
        OngoingMatch currentMatch = ongoingMatches.get(uuid);

        //todo mb add returning value
        matchScoreCalculationService.updateMatchScore(currentMatch, pointScoreDto.getPointWinner());

        currentMatch.getState().setEnded(true);
        currentMatch.setWinner(currentMatch.getFirstPlayer());

        return ongoingMatchMapper.toDto(currentMatch);
    }


    public void removeMatchById(UUID uuid) {
        ongoingMatches.remove(uuid);
    }
}
