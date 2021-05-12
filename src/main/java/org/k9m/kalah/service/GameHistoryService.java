package org.k9m.kalah.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.k9m.kalah.api.model.GameHistoryResponse;
import org.k9m.kalah.persistence.model.Game;
import org.k9m.kalah.persistence.model.GameHistory;
import org.k9m.kalah.persistence.repository.GameHistoryRepository;
import org.k9m.kalah.service.exception.GameHistoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameHistoryService {

    private final GameHistoryRepository gameHistoryRepository;
    private final ModelConverter modelConverter;

    public void saveGameHistory(final Game game) {
        GameHistory gameHistory = gameHistoryRepository.findFirstByGameId(game.getGameId());
        if(gameHistory == null){
            gameHistory = new GameHistory()
                    .setGameId(game.getGameId())
                    .setHistory(Collections.singletonList(game.getBoardStatus()));
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
            return modelConverter.toGameHistoryResponse(gameId, gameHistory);
        }
    }

}
