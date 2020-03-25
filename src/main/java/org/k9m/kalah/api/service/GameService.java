package org.k9m.kalah.api.service;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.api.exception.GameNotFoundException;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.api.service.board.Game;
import org.k9m.kalah.config.HostProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameService {

    @Autowired
    private final HostProperties hostProperties;


    private Map<String, Game> games = new HashMap<>();

    public CreateGameResponse createGame() {
        final String gameId = UUID.randomUUID().toString();
        games.put(gameId, new Game(gameId));

        return new CreateGameResponse()
                .id(gameId)
                .link(hostProperties.getBaseUrl() + "/games/" + gameId);
    }

    public GameStatus executeMove(final String gameId, final Integer pitNumber) {
        final Game game = games.get(gameId);
        if(game == null){
            throw new GameNotFoundException("Game with Id: " + gameId + " not found!");
        }

        return new GameStatus()
                .id(gameId)
                .link(hostProperties.getBaseUrl() + "/games/" + gameId)
                .status(game.executeMove(pitNumber));

    }

    public void reset() {
        games = new HashMap<>();
    }
}
