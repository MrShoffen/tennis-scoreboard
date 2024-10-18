package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.mapper.MatchMapper;
import org.mrshoffen.dto.response.MatchResponseDto;
import org.mrshoffen.repository.MatchRepository;

import java.util.List;

import static java.util.stream.Collectors.*;

public class MatchService {

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    @Inject
    public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }


    public List<MatchResponseDto> findMatchesWithPagination(int pageNumber, int pageSize) {


        return matchRepository.findWithPagination(pageNumber, pageSize).stream()
                .map(matchMapper::toDto)
                .collect(toList());
    }

    public long sizeAllMatches() {
        return matchRepository.size();
    }

    public List<MatchResponseDto> findAllMatches() {
        return matchRepository.findAll().stream()
                .map(matchMapper::toDto)
                .collect(toList());
    }

}
