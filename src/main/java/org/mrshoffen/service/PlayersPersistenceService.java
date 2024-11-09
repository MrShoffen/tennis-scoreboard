package org.mrshoffen.service;

import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.mrshoffen.dto.request.PageRequestDto;
import org.mrshoffen.dto.response.pageable.PageResponseDto;
import org.mrshoffen.entity.persistence.Player;
import org.mrshoffen.exception.ValidationException;
import org.mrshoffen.mapper.PlayerMapper;
import org.mrshoffen.repository.MatchRepository;
import org.mrshoffen.repository.PlayerRepository;

import static java.lang.Math.ceilDiv;

public class PlayersPersistenceService {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    private final PlayerMapper playerMapper;
    private final Validator validator;

    @Inject
    public PlayersPersistenceService(PlayerRepository playerRepository, MatchRepository matchRepository, PlayerMapper playerMapper, Validator validator) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.playerMapper = playerMapper;
        this.validator = validator;
    }

    public PageResponseDto findPageFilteredByName(PageRequestDto requestDto) {

        var validationResult = validator.validate(requestDto);

        if (!validationResult.isEmpty()) {
            throw new ValidationException(validationResult);
        }


        Integer pageNumber = requestDto.getPageNumber();
        Integer pageSize = requestDto.getPageSize();
        String nameForFilter = requestDto.getPlayerNameFilterBy();

        var players = playerRepository.getAllWithOffsetAndLimit(
                        (pageNumber - 1) * pageSize, pageSize, nameForFilter)
                .stream()
                .map(playerMapper::toDto)
                .peek(dto -> dto.setMatchesPlayed(matchRepository.numberOfEntitiesContainingName(dto.getName())))
                .peek(dto ->dto.setMatchesWon(matchRepository.numberOfWonMathcesByPlayerName(dto.getName())))
                .toList();

        int totalPages = ceilDiv(playerRepository.numberOfEntitiesContainingName(nameForFilter), pageSize);


        return PageResponseDto.builder()
                .entities(players)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .build();
    }


    public Player findByNameOrSave(String name) {
        Player playerForSave = Player.builder().name(name).build();

        return playerRepository.save(playerForSave)
                .orElseGet(() -> playerRepository.findByName(name).get());

    }
}
