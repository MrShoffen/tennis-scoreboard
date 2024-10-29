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



    public List<MatchResponseDto> findAllMatches() {
        return matchRepository.findAll().stream()
                .map(matchMapper::toDto)
                .collect(toList());
    }

    public PageResponseDto findPageFilteredByName(PageRequestDto requestDto) {
        //todo add validation


        List<MatchResponseDto> matches = matchRepository.findWithPaginationFilteredByName(
                        requestDto.getPageNumber(),
                        requestDto.getPageSize(),
                        requestDto.getPlayerName()
                ).stream()
                .map(matchMapper::toDto)
                .collect(toList());

        int totalPages = (int) ceilDiv(matchRepository.numberOfEntitiesWithName(requestDto.getPlayerName()),
                requestDto.getPageSize());

        return  PageResponseDto.builder()
                .entities(matches)
                .pageNumber(requestDto.getPageNumber())
                .pageSize(requestDto.getPageSize())
                .totalPages(totalPages)
                .build();

    }


}
