package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.NewMatchRequestDto;
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

    @Inject
    public OngoingMatchesService(MatchRepository matchRepository, PlayerRepository playerRepository, OngoingMatchMapper ongoingMatchMapper) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.ongoingMatchMapper = ongoingMatchMapper;
    }


    public UUID createMatch(NewMatchRequestDto newMatchRequestDto) {

        var firstPlayer = Player.builder()
                .name(newMatchRequestDto.getFirstPlayer())
                .build();

        var secondPlayer = Player.builder()
                .name(newMatchRequestDto.getSecondPlayer())
                .build();

        var match = new OngoingMatch(firstPlayer,secondPlayer);

        UUID uuid = UUID.randomUUID();

        ongoingMatches.put(uuid, match);

        return uuid;
    }

    public OngoingMatchResponseDto getMatchById(UUID uuid) {
        //todo handle non existing match

        var match = ongoingMatches.get(uuid);
        return ongoingMatchMapper.toDto(match);

    }


    public void saveMatch(Match match) {
        Player firstPlayer = playerRepository.findByName(match.getFirstPlayer().getName())
                .orElseGet(() -> playerRepository.save(match.getFirstPlayer()));

        Player secondPlayer = playerRepository.findByName(match.getSecondPlayer().getName())
                .orElseGet(() -> playerRepository.save(match.getSecondPlayer()));

        System.out.println();


        match.setFirstPlayer(firstPlayer);
        match.setWinner(firstPlayer);
        match.setSecondPlayer(secondPlayer);

        matchRepository.save(match);

    }
}
