package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.pageable.PageResponseDto;
import org.mrshoffen.dto.response.pageable.PlayerResponseDto;
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


        Integer pageNumber = requestDto.getPageNumber();
        Integer pageSize = requestDto.getPageSize();
        String playerName = requestDto.getPlayerName();

        List<PlayerResponseDto> players = playerRepository.getAllWithOffsetAndLimit(
                        (pageNumber - 1) * pageSize, pageSize, playerName).stream()
                .map(playerMapper::toDto)
                .peek(dto -> dto.setMatchesPlayed(matchRepository.numberOfEntitiesWithName(dto.getName())))
                .toList();

        int totalPages = ceilDiv(playerRepository.numberOfEntitiesWithName(playerName), pageSize);


        return PageResponseDto.builder()
                .entities(players)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .build();
    }


}
