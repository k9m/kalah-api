package org.k9m.kalah.api;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameController implements GamesApi{

    @Autowired
    private final GameService gameService;


    @Override
    public ResponseEntity<CreateGameResponse> createGame() {
        return ResponseEntity.ok(gameService.createGame());
    }

    @Override
    public ResponseEntity<GameStatus> currentBalance(final String gameId, final Integer pitId) {
        return ResponseEntity.ok(gameService.executeMove(gameId, pitId));
    }
}
