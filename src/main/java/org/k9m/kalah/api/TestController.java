package org.k9m.kalah.api;

import lombok.RequiredArgsConstructor;
import org.k9m.kalah.api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Profile("!prod")
public class TestController implements TestApi{

    @Autowired
    private final GameService gameService;

    @Override
    public ResponseEntity<Void> removeGames() {
        gameService.reset();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
