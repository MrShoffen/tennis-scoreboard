package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.dto.request.PointScoreDto;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.domain.OngoingMatch;
import org.mrshoffen.entity.persistence.Player;
import org.mrshoffen.exception.EntityNotFoundException;
import org.mrshoffen.mapper.OngoingMatchMapper;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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

        OngoingMatch ongoingMatch = ongoingMatches.get(uuid);

        if(ongoingMatch == null){
            throw new EntityNotFoundException("No Match with such id!");
        }

        return ongoingMatchMapper.toDto(ongoingMatch);
    }


    public OngoingMatchResponseDto updateMatch(UUID uuid, PointScoreDto pointScoreDto) {

        //todo add validation
        OngoingMatch currentMatch = ongoingMatches.get(uuid);

        //todo mb add returning value
        matchScoreCalculationService.updateMatchScore(currentMatch, pointScoreDto.getPointWinner());

//        currentMatch.getMatchState().setEnded(true);
//        currentMatch.setWinner(currentMatch.getFirstPlayer());

        return ongoingMatchMapper.toDto(currentMatch);
    }


    public void removeMatchById(UUID uuid) {
        ongoingMatches.remove(uuid);
    }
}
