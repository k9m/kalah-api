package org.k9m.kalah.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.k9m.kalah.api.exception.GameHistoryNotFoundException;
import org.k9m.kalah.api.model.GameHistoryResponse;
import org.k9m.kalah.api.model.Pits;
import org.k9m.kalah.persistence.model.Game;
import org.k9m.kalah.persistence.model.GameHistory;
import org.k9m.kalah.persistence.repository.GameHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameHistoryService {

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    public void saveGameHistory(final Game game) {
        GameHistory gameHistory = gameHistoryRepository.findFirstByGameId(game.getGameId());
        if(gameHistory == null){
            gameHistory = new GameHistory()
                    .setGameId(game.getGameId())
                    .setHistory(Arrays.asList(game.getBoardStatus()));
        }
        else{
            gameHistory.appendHistory(game.getBoardStatus());
        }

        gameHistoryRepository.save(gameHistory);
        log.info("Game history saved: {}", gameHistory);
    }

    public GameHistoryResponse getGameHistory(final String gameId) {
        GameHistory gameHistory = gameHistoryRepository.findFirstByGameId(gameId);
        if(gameHistory == null){
            throw new GameHistoryNotFoundException("GameHistory for Game with gameId: " + gameId + " not found!");
        }
        else{
            return new GameHistoryResponse()
                    .gameId(gameId)
                    .history(gameHistory.getHistory().stream()
                            .map(gh -> new Pits().moves(gh.getPits()))
                            .collect(Collectors.toList()));
        }
    }

}
