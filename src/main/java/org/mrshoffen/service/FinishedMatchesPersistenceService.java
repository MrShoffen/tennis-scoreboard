package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.PageResponseDto;
import org.mrshoffen.mapper.MatchMapper;
import org.mrshoffen.dto.response.MatchResponseDto;
import org.mrshoffen.repository.MatchRepository;

import java.util.List;

import static java.lang.Integer.*;
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

    public PageResponseDto findMatchesWithPaginationFilteredByName(PageRequestDto requestDto) {
        //todo add validation


        List<MatchResponseDto> matches = matchRepository.findWithPaginationFilteredByName(
                        parseInt(requestDto.pageNumber()),
                        parseInt(requestDto.pageSize()),
                        requestDto.playerName()
                ).stream()
                .map(matchMapper::toDto)
                .collect(toList());

        long totalPages = Math.ceilDiv(matchRepository.numberOfEntitiesWithName(requestDto.playerName()),
                parseInt(requestDto.pageSize()));

        return new PageResponseDto(matches, totalPages);
    }


}
