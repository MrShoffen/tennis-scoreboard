package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.PageResponseDto;
import org.mrshoffen.dto.response.PlayerResponseDto;
import org.mrshoffen.mapper.PlayerMapper;
import org.mrshoffen.repository.MatchRepository;
import org.mrshoffen.repository.PlayerRepository;

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

    //todo problem with LIKE -
    public PageResponseDto findPageFilteredByName(PageRequestDto requestDto) {
        //todo add validation


        List<PlayerResponseDto> players = playerRepository.findWithPaginationFilteredByName(
                        requestDto.getPageNumber(),
                        requestDto.getPageSize(),
                        requestDto.getPlayerName()
                ).stream()
                .map(playerMapper::toDto)
                .peek(dto -> dto.setMatchesPlayed(matchRepository.numberOfEntitiesWithName(dto.getName())))
                .toList();

        int totalPages = (int) ceilDiv(playerRepository.numberOfEntitiesWithName(requestDto.getPlayerName()),
                requestDto.getPageSize());


        return  PageResponseDto.builder()
                .entities(players)
                .pageNumber(requestDto.getPageNumber())
                .pageSize(requestDto.getPageSize())
                .totalPages(totalPages)
                .build();
    }


}
