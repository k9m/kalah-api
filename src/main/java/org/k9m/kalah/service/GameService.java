package org.k9m.kalah.service;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.service.exception.GameNotFoundException;
import org.k9m.kalah.service.exception.GameOverException;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.service.game.GameFactory;
import org.k9m.kalah.config.HostProperties;
import org.k9m.kalah.persistence.model.Game;
import org.k9m.kalah.persistence.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameService {

    private final HostProperties hostProperties;
    private final GameRepository gameRepository;

    public CreateGameResponse createGame() {
        final Game newGame = gameRepository.save(GameFactory.createGame());

        final String gameId = newGame.getGameId();
        return new CreateGameResponse()
                .id(gameId)
                .link(hostProperties.getBaseUrl() + "/games/" + gameId);
    }

    public GameStatus executeMove(final String gameId, final Integer pitNumber) {
        final Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game with Id: " + gameId + " not found!"));
        if(!"ACTIVE".equalsIgnoreCase(game.getGameStatus())){
            throw new GameOverException("Game is over, no further moves can be made, status is: " + game.getGameStatus());
        }

        final Game executedGame = gameRepository.save(GameFactory.executeMove(game, pitNumber));
        return toStatus(executedGame);

    }

    public GameStatus getStatus(final String gameId) {
        final Game game =  gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game with Id: " + gameId + " not found!"));
        return toStatus(game);
    }

    private GameStatus toStatus(final Game game){
        final Map<String, String> status = new LinkedHashMap<>();
        final AtomicInteger index = new AtomicInteger(1);
        game.getBoardStatus().getPits().forEach(p -> status.put(String.valueOf(index.getAndIncrement()), String.valueOf(p)));

        return new GameStatus()
            .id(game.getGameId())
            .state(game.getGameStatus())
            .link(hostProperties.getBaseUrl() + "/games/" + game.getGameId())
            .playerTurn(game.getPlayerTurn())
            .status(status);
    }
}
