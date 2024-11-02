package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.dto.request.PointScoreDto;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.Match;
import org.mrshoffen.entity.OngoingMatch;
import org.mrshoffen.entity.Player;
import org.mrshoffen.mapper.OngoingMatchMapper;
import org.mrshoffen.repository.MatchRepository;
import org.mrshoffen.repository.PlayerRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private static final Map<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    private final OngoingMatchMapper ongoingMatchMapper;

    private final MatchScoreCalculationService matchScoreCalculationService;

    @Inject
    public OngoingMatchesService(MatchRepository matchRepository, PlayerRepository playerRepository, OngoingMatchMapper ongoingMatchMapper, MatchScoreCalculationService matchScoreCalculationService) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
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

        var match = new OngoingMatch(firstPlayer, secondPlayer);



        UUID uuid = UUID.randomUUID();

        ongoingMatches.put(uuid, match);

        return uuid;
    }

    public OngoingMatchResponseDto getMatchById(UUID uuid) {
        //todo handle non existing match

        return ongoingMatchMapper.toDto(ongoingMatches.get(uuid));


    }


    public void saveMatch(UUID uuid) {

        OngoingMatch currentMatch = ongoingMatches.get(uuid);


        Player firstPlayer = playerRepository.findByName(currentMatch.getFirstPlayer().getName())
                .orElseGet(() -> playerRepository.save(currentMatch.getFirstPlayer()));

        Player secondPlayer = playerRepository.findByName(currentMatch.getSecondPlayer().getName())
                .orElseGet(() -> playerRepository.save(currentMatch.getSecondPlayer()));

        Player winner = playerRepository.findByName(currentMatch.getWinner().getName()).get();



        Match match = Match.builder()
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .winner(winner)
                .build();

        matchRepository.save(match);

    }

    public OngoingMatchResponseDto updateMatch(UUID uuid, PointScoreDto pointScoreDto) {

        //todo add validation
        OngoingMatch currentMatch = ongoingMatches.get(uuid);

        matchScoreCalculationService.updateMatchScore(currentMatch, pointScoreDto.getPointWinner());

        //
//        currentMatch.wonBy(currentMatch.getFirstPlayer().getName());

        return ongoingMatchMapper.toDto(currentMatch);

    }


}
