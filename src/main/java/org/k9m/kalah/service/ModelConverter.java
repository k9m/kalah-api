package org.k9m.kalah.service;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.api.model.GameHistoryResponse;
import org.k9m.kalah.api.model.GameStatus;
import org.k9m.kalah.api.model.Pits;
import org.k9m.kalah.config.HostProperties;
import org.k9m.kalah.persistence.model.Game;
import org.k9m.kalah.persistence.model.GameHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class ModelConverter {

    private final HostProperties hostProperties;

    GameStatus toStatus(final Game game){
        final AtomicInteger index = new AtomicInteger(1);
        final Map<String, String> status = game.getBoardStatus().getPits().stream()
            .collect(Collectors.toMap(k -> String.valueOf(index.getAndIncrement()), Object::toString));

        return new GameStatus()
            .id(game.getGameId())
            .state(game.getGameStatus())
            .link(hostProperties.getBaseUrl() + "/games/" + game.getGameId())
            .playerTurn(game.getPlayerTurn())
            .status(status);
    }

    CreateGameResponse toGameResponse(final Game game){
        final String gameId = game.getGameId();
        return new CreateGameResponse()
            .id(gameId)
            .link(hostProperties.getBaseUrl() + "/games/" + gameId);
    }

    GameHistoryResponse toGameHistoryResponse(final String gameId, final GameHistory gameHistory){
        return new GameHistoryResponse()
            .gameId(gameId)
            .history(gameHistory.getHistory().stream()
                .map(gh -> new Pits().moves(gh.getPits()))
                .collect(Collectors.toList()));
    }

}
