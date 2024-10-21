package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.PlayerPageRequestDto;
import org.mrshoffen.dto.response.MatchResponseDto;
import org.mrshoffen.dto.response.PageResponseDto;
import org.mrshoffen.dto.response.PlayerResponseDto;
import org.mrshoffen.mapper.PlayerMapper;
import org.mrshoffen.repository.PlayerRepository;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class PlayerService {

    private final PlayerRepository playerRepository;

    private final PlayerMapper playerMapper;

    @Inject
    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }


    public List<MatchResponseDto> findAllMatches() {
        return Collections.emptyList();
    }

    public PageResponseDto findWithPaginationFilteredByName(PlayerPageRequestDto requestDto) {
        //todo add validation


        List<PlayerResponseDto> players = playerRepository.findWithPagination(
                        parseInt(requestDto.pageNumber()),
                        parseInt(requestDto.pageSize()),
                        requestDto.playerName()
                ).stream()
                .map(playerMapper::toDto)
                .toList();

        long maxNumber = Math.ceilDiv(playerRepository.sizeFilteredByPlayerName(requestDto.playerName()),
                parseInt(requestDto.pageSize()));

        return new PageResponseDto(players,maxNumber);
    }

}
