package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.pageable.PageResponseDto;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.domain.OngoingMatch;
import org.mrshoffen.entity.persistence.Match;
import org.mrshoffen.entity.persistence.Player;
import org.mrshoffen.exception.EntityNotFoundException;
import org.mrshoffen.mapper.MatchMapper;
import org.mrshoffen.dto.response.pageable.MatchResponseDto;
import org.mrshoffen.repository.MatchRepository;

import java.util.List;

import static java.lang.Math.*;
import static java.util.stream.Collectors.*;

public class FinishedMatchesPersistenceService {

    private final MatchRepository matchRepository;

    private final MatchMapper matchMapper;

    private final PlayersPersistenceService playersPersistenceService;

    @Inject
    public FinishedMatchesPersistenceService(MatchRepository matchRepository, MatchMapper matchMapper, PlayersPersistenceService playersPersistenceService) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
        this.playersPersistenceService = playersPersistenceService;
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

    public MatchResponseDto saveFinishedMatch(OngoingMatchResponseDto finishedMatch) {
        Player firstPlayer = playersPersistenceService.findByNameOrSave(finishedMatch.getFirstPlayer());
        Player secondPlayer = playersPersistenceService.findByNameOrSave(finishedMatch.getSecondPlayer());
        Player winner = playersPersistenceService.findByNameOrSave(finishedMatch.getWinner());

        Match match = Match.builder()
                .firstPlayer(firstPlayer)
                .secondPlayer(secondPlayer)
                .winner(winner)
                .build();

        matchRepository.save(match);

        return matchMapper.toDto(match);
    }


    public MatchResponseDto findById(Integer id) {
        //todo add validation

        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error! No such match with id: " + id));


        return matchMapper.toDto(match);

    }
}
