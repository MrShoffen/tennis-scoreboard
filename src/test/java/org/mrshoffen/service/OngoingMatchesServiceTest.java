package org.mrshoffen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.dto.request.PointScoreDto;
import org.mrshoffen.dto.response.score.OngoingMatchDto;
import org.mrshoffen.entity.domain.PlayerNumber;
import org.mrshoffen.exception.EntityNotFoundException;
import org.mrshoffen.exception.ValidationException;
import org.mrshoffen.utils.DependencyManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OngoingMatchesServiceTest {

    OngoingMatchesService ongoingMatchesService;

    NewMatchRequestDto newMatch;



    @BeforeEach
    void initialize() {
        ongoingMatchesService = DependencyManager.getInjector().getInstance(OngoingMatchesService.class);
        newMatch = new NewMatchRequestDto();
    }

    @Test
    void createNewMatch_Success_IfNamesCorrect() {
        newMatch.setFirstPlayer("first");
        newMatch.setSecondPlayer("second");

        UUID uuid = ongoingMatchesService.createMatch(newMatch);

        assertThat(uuid).isNotNull();

        assertThat(ongoingMatchesService.getMatchById(uuid).getFirstPlayer())
                .isEqualTo("first");

        assertThat(ongoingMatchesService.getMatchById(uuid).getSecondPlayer())
                .isEqualTo("second");
    }

    @Test
    void createNewMatch_Throw_IfOnePlayerIsNull() {
        newMatch.setFirstPlayer("first");


        assertThatThrownBy(() -> ongoingMatchesService.createMatch(newMatch))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Name can't be null!");
    }

    @Test
    void createNewMatch_Throw_IfPlayerNameIsIncorrect() {
        newMatch.setFirstPlayer("first");


        newMatch.setSecondPlayer("sec");
        assertThatThrownBy(() -> ongoingMatchesService.createMatch(newMatch))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Incorrect name length! Must be between 4 and 30");


        newMatch.setSecondPlayer("...34.23.-");
        assertThatThrownBy(() -> ongoingMatchesService.createMatch(newMatch))
                .isInstanceOf(ValidationException.class)
                .hasMessage("Incorrect symbols in name!");
    }

    @Test
    void getMatchById_Throw_IfIdIsNull(){

        assertThatThrownBy(() -> ongoingMatchesService.getMatchById(null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Match id is null!");


    }

    @Test
    void getMatchById_Throw_IfIdIsNotSaved(){

        UUID randomUUID = UUID.randomUUID();

        assertThatThrownBy(() -> ongoingMatchesService.getMatchById(randomUUID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No Match with such id!");

    }

    @Test
    void updateMatch_Success_IfMatchAndPlayerNameArePresented() {
        newMatch.setFirstPlayer("first");
        newMatch.setSecondPlayer("second");

        UUID uuid = ongoingMatchesService.createMatch(newMatch);

        PointScoreDto scoreDto = new PointScoreDto();
        scoreDto.setPointWinner("first");

        OngoingMatchDto updatedMatch = ongoingMatchesService.updateMatch(uuid, scoreDto);

        assertThat(updatedMatch.getCurrentPoints().get(PlayerNumber.ONE))
                .isEqualTo(1);

    }

    @Test
    void updateMatch_Throw_IfNameIsNot() {
        newMatch.setFirstPlayer("first");
        newMatch.setSecondPlayer("second");

        UUID uuid = ongoingMatchesService.createMatch(newMatch);

        PointScoreDto scoreDto = new PointScoreDto();
        scoreDto.setPointWinner("third");

        assertThatThrownBy(() -> ongoingMatchesService.updateMatch(uuid, scoreDto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No player with name third in this match!");
    }


}