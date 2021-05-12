package org.k9m.kalah.service;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.persistence.model.Game;
import org.k9m.kalah.persistence.repository.GameRepository;
import org.k9m.kalah.service.exception.GameNotFoundException;
import org.k9m.kalah.service.exception.GameOverException;
import org.k9m.kalah.service.game.GameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameService {

    private final ModelConverter modelConverter;
    private final GameRepository gameRepository;

    public CreateGameResponse createGame() {
        final Game newGame = gameRepository.save(GameFactory.createGame());
        return modelConverter.toGameResponse(newGame);
    }

    public GameStatus executeMove(final String gameId, final Integer pitNumber) {
        final Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game with Id: " + gameId + " not found!"));
        if(!"ACTIVE".equalsIgnoreCase(game.getGameStatus())){
            throw new GameOverException("Game is over, no further moves can be made, status is: " + game.getGameStatus());
        }

        final Game executedGame = gameRepository.save(GameFactory.executeMove(game, pitNumber));
        return modelConverter.toStatus(executedGame);

    }

    public GameStatus getStatus(final String gameId) {
        final Game game =  gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game with Id: " + gameId + " not found!"));
        return modelConverter.toStatus(game);
    }

}
