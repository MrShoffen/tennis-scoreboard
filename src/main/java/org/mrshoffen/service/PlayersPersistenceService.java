package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.MatchResponseDto;
import org.mrshoffen.dto.response.PageResponseDto;
import org.mrshoffen.dto.response.PlayerResponseDto;
import org.mrshoffen.entity.Match;
import org.mrshoffen.mapper.PlayerMapper;
import org.mrshoffen.repository.MatchRepository;
import org.mrshoffen.repository.PlayerRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Math.*;

public class PlayersPersistenceService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    private final PlayerMapper playerMapper;

    @Inject
    public PlayersPersistenceService(PlayerRepository playerRepository, MatchRepository matchRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.playerMapper = playerMapper;
    }


    public List<MatchResponseDto> findAllMatches() {
        return Collections.emptyList();
    }
//todo problem with LIKE -
    public PageResponseDto findWithPaginationFilteredByName(PageRequestDto requestDto) {
        //todo add validation


        List<PlayerResponseDto> players = playerRepository.findWithPaginationFilteredByName(
                        parseInt(requestDto.pageNumber()),
                        parseInt(requestDto.pageSize()),
                        requestDto.playerName()
                ).stream()
                .peek(player -> player.setMatchesPlayed(matchRepository.numberOfEntitiesWithName(player.getName())))
                .map(playerMapper::toDto)
                .toList();

        long totalPagesNumber = ceilDiv(playerRepository.numberOfEntitiesWithName(requestDto.playerName()),
                parseInt(requestDto.pageSize()));


        return new PageResponseDto(players, totalPagesNumber);
    }

    private double calculateWinRate(String name) {
        long matchesSize = matchRepository.numberOfEntitiesWithName(name);
        List<Match> allMatches = matchRepository.findWithPaginationFilteredByName(1, (int) matchesSize, name);

        long matchesWin = allMatches.stream()
                .filter(match -> match.getWinner().getName().equals(name))
                .count();

        double v = BigDecimal.valueOf((double) matchesWin / matchesSize).setScale(4, RoundingMode.HALF_UP).doubleValue();
        return v;

    }

}
