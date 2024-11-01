package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.PageResponseDto;
import org.mrshoffen.mapper.MatchMapper;
import org.mrshoffen.dto.response.MatchResponseDto;
import org.mrshoffen.repository.MatchRepository;

import java.util.List;

import static java.lang.Math.*;
import static java.util.stream.Collectors.*;

public class FinishedMatchesPersistenceService {

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    @Inject
    public FinishedMatchesPersistenceService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }


    public PageResponseDto findPageFilteredByName(PageRequestDto requestDto) {
        //todo add validation

        Integer pageNumber = requestDto.getPageNumber();
        Integer pageSize = requestDto.getPageSize();
        String playerName = requestDto.getPlayerName();

        List<MatchResponseDto> matches = matchRepository.getAllWithOffsetAndLimit(
                        (pageNumber - 1) * pageSize, pageSize, playerName).stream()
                .map(matchMapper::toDto)
                .collect(toList());

        int totalPages = ceilDiv(matchRepository.numberOfEntitiesWithName(playerName), pageSize);

        return PageResponseDto.builder()
                .entities(matches)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .build();

    }


}
