package org.mrshoffen.service;

import jakarta.inject.Inject;
import org.mrshoffen.dto.request.NewMatchRequestDto;
import org.mrshoffen.entity.Match;
import org.mrshoffen.entity.Player;
import org.mrshoffen.repository.MatchRepository;
import org.mrshoffen.repository.PlayerRepository;

import java.util.UUID;

public class OngoingMatchesService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    @Inject
    public OngoingMatchesService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }


    public UUID createMatch(NewMatchRequestDto newMatchRequestDto) {


        Player player1 = Player.builder().name(newMatchRequestDto.getFirstPlayer()).build();
        Player player2 = Player.builder().name(newMatchRequestDto.getSecondPlayer()).build();
        Match match = Match.builder()
                .firstPlayer(player1)
                .secondPlayer(player2)
                .winner(player1)
                .build();

        saveMatch(match);

        return UUID.randomUUID();
    }


    public void saveMatch(Match match) {
        Player firstPlayer = playerRepository.findByName(match.getFirstPlayer().getName())
                .orElseGet(() -> playerRepository.save(match.getFirstPlayer()));

        Player secondPlayer = playerRepository.findByName(match.getSecondPlayer().getName())
                .orElseGet(() -> playerRepository.save(match.getSecondPlayer()));

        System.out.println();


        match.setFirstPlayer(firstPlayer);
        match.setWinner(firstPlayer);
        match.setSecondPlayer(secondPlayer);

        matchRepository.save(match);

    }
}
