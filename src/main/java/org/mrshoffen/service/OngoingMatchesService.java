package org.mrshoffen.service;

import jakarta.inject.Inject;
import jakarta.validation.Validator;
import lombok.Getter;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.dto.request.PointScoreDto;
import org.mrshoffen.dto.response.score.OngoingMatchDto;
import org.mrshoffen.entity.domain.OngoingMatch;
import org.mrshoffen.entity.domain.PlayerNumber;
import org.mrshoffen.entity.persistence.Player;
import org.mrshoffen.exception.EntityNotFoundException;
import org.mrshoffen.exception.ValidationException;
import org.mrshoffen.mapper.OngoingMatchMapper;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    @Getter
    private static final Map<UUID, OngoingMatch> ongoingMatches = new ConcurrentHashMap<>();

    private final OngoingMatchMapper ongoingMatchMapper;

    private final Validator validator;


    @Inject
    public OngoingMatchesService(OngoingMatchMapper ongoingMatchMapper, Validator validator) {
        this.ongoingMatchMapper = ongoingMatchMapper;
        this.validator = validator;
    }


    public UUID createMatch(NewMatchRequestDto newMatchRequestDto) {

        var validationResult = validator.validate(newMatchRequestDto);

        if (!validationResult.isEmpty()) {
            throw new ValidationException(validationResult);
        }

        var firstPlayer = Player.builder()
                .name(newMatchRequestDto.getFirstPlayer())
                .build();

        var secondPlayer = Player.builder()
                .name(newMatchRequestDto.getSecondPlayer())
                .build();

        var match = new OngoingMatch(firstPlayer.getName(), secondPlayer.getName());


        UUID uuid = UUID.randomUUID();

        ongoingMatches.put(uuid, match);

        return uuid;
    }

    public OngoingMatchDto getMatchById(UUID uuid) {

        OngoingMatch ongoingMatch = tryToGetMatchById(uuid);

        return ongoingMatchMapper.toDto(ongoingMatch);
    }


    public OngoingMatchDto updateMatch(UUID uuid, PointScoreDto pointScoreDto) {

        OngoingMatch currentMatch = tryToGetMatchById(uuid);

        PlayerNumber pointWinnerNo = tryToGetPlayerNumberByName(pointScoreDto.getPointWinner(), currentMatch);

        currentMatch.scorePointToPlayer(pointWinnerNo);

        return ongoingMatchMapper.toDto(currentMatch);
    }


    public void removeMatchById(UUID uuid) {
        ongoingMatches.remove(uuid);
    }

    private OngoingMatch tryToGetMatchById(UUID uuid) {
        if (uuid == null) {
            throw new EntityNotFoundException("Match id is null!");
        }

        OngoingMatch ongoingMatch = ongoingMatches.get(uuid);

        if (ongoingMatch == null) {
            throw new EntityNotFoundException("No Match with such id!");
        }

        return ongoingMatch;
    }

    public PlayerNumber tryToGetPlayerNumberByName(String name, OngoingMatch match) {
        if (name.equals(match.getFirstPlayer())) {
            return PlayerNumber.ONE;
        } else if (name.equals(match.getSecondPlayer())) {
            return PlayerNumber.TWO;
        } else {
            throw new EntityNotFoundException("No player with name " + name + " in this match!");
        }
    }

}
