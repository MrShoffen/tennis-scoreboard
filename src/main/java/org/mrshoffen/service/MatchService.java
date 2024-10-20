package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.response.MatchPageResponseDto;
import org.mrshoffen.dto.request.MatchPageRequestDto;
import org.mrshoffen.mapper.MatchMapper;
import org.mrshoffen.dto.response.MatchResponseDto;
import org.mrshoffen.repository.MatchRepository;

import java.util.List;

import static java.lang.Integer.*;
import static java.util.stream.Collectors.*;

public class MatchService {

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    @Inject
    public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }




    public List<MatchResponseDto> findAllMatches() {
        return matchRepository.findAll().stream()
                .map(matchMapper::toDto)
                .collect(toList());
    }

    public MatchPageResponseDto findMatchesWithPaginationFilteredByName(MatchPageRequestDto requestDto) {
        //todo add validation


        List<MatchResponseDto> matches = matchRepository.findWithPagination(
                        parseInt(requestDto.pageNumber()),
                        parseInt(requestDto.pageSize()),
                        requestDto.playerName()
                ).stream()
                .map(matchMapper::toDto)
                .collect(toList());

        long maxNumber = Math.ceilDiv(matchRepository.sizeFilteredByPlayerName(requestDto.playerName()),
                parseInt(requestDto.pageSize()));

        return new MatchPageResponseDto(matches, maxNumber);
    }


}
