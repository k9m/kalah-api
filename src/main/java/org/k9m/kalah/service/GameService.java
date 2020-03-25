package org.k9m.kalah.service;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.api.model.CreateGameResponse;
import org.k9m.kalah.config.HostProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameService {

    @Autowired
    private final HostProperties hostProperties;


    public CreateGameResponse createGame() {
        final String gameId = UUID.randomUUID().toString();
        return new CreateGameResponse()
                .id(gameId)
                .link(hostProperties.getBaseUrl() + "/games/" + gameId);
    }
}
