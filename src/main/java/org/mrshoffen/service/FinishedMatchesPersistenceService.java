package org.mrshoffen.service;

import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.pageable.PageResponseDto;
import org.mrshoffen.dto.response.score.OngoingMatchResponseDto;
import org.mrshoffen.entity.persistence.Match;
import org.mrshoffen.entity.persistence.Player;
import org.mrshoffen.exception.EntityNotFoundException;
import org.mrshoffen.exception.ValidationException;
import org.mrshoffen.mapper.MatchMapper;
import org.mrshoffen.dto.response.pageable.MatchResponseDto;
import org.mrshoffen.repository.MatchRepository;

import java.util.List;

import static java.lang.Math.*;
import static java.util.stream.Collectors.*;

public class FinishedMatchesPersistenceService {

    private final MatchRepository matchRepository;

    private final PlayersPersistenceService playersPersistenceService;

    private final MatchMapper matchMapper;

    private final Validator validator;

    @Inject
    public FinishedMatchesPersistenceService(MatchRepository matchRepository, MatchMapper matchMapper, PlayersPersistenceService playersPersistenceService, Validator validator) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
        this.playersPersistenceService = playersPersistenceService;
        this.validator = validator;
    }


    public PageResponseDto findPageFilteredByName(PageRequestDto requestDto) {

        var validationResult = validator.validate(requestDto);

        if(!validationResult.isEmpty()){
            throw new ValidationException(validationResult);
        }

        Integer pageNumber = requestDto.getPageNumber();
        Integer pageSize = requestDto.getPageSize();
        String nameForFilter = requestDto.getPlayerNameFilterBy();

        List<MatchResponseDto> matches = matchRepository.getAllWithOffsetAndLimit(
                        (pageNumber - 1) * pageSize, pageSize, nameForFilter)
                .stream()
                .map(matchMapper::toDto)
                .collect(toList());

        int totalPages = ceilDiv(matchRepository.numberOfEntitiesContainingName(nameForFilter), pageSize);

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

        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error! No such Match with id: " + id));


        return matchMapper.toDto(match);
    }
}
