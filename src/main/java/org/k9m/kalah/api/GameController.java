package org.k9m.kalah.api;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.api.model.GameHistoryResponse;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.api.service.GameHistoryService;
import org.k9m.kalah.api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameController implements GamesApi{

    private final GameService gameService;
    private final GameHistoryService gameHistoryService;

    @Override
    public ResponseEntity<CreateGameResponse> createGame() {
        return ResponseEntity.ok(gameService.createGame());
    }

    @Override
    public ResponseEntity<GameStatus> executeMove(final String gameId, final Integer pitId) {
        return ResponseEntity.ok(gameService.executeMove(gameId, pitId));
    }

    @Override
    public ResponseEntity<GameHistoryResponse> getHistory(String gameId) {
        return ResponseEntity.ok(gameHistoryService.getGameHistory(gameId));
    }

    @Override
    public ResponseEntity<GameStatus> getStatus(final String gameId) {
        return ResponseEntity.ok(gameService.getStatus(gameId));
    }
}
