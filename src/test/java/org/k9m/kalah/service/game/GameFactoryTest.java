package org.k9m.kalah.service.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.k9m.kalah.service.game.exception.InvalidPitExceptionException;
import org.k9m.kalah.service.game.exception.NoStonesInPitException;
import org.k9m.kalah.service.game.exception.WrongPlayerTurnException;
import org.k9m.kalah.persistence.model.Game;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameFactoryTest {

    private Game game;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Game mockGame;



    @BeforeEach
    public void setUp() {
        game = GameFactory.createGame();
    }

    @Test
    public void createGame() {
        assertThat("Number of pits are 14", game.getBoardStatus().getPits().size(), is(14));
    }

    @Test
    public void executeMoveFromKalah() {
        assertThrows(InvalidPitExceptionException.class, () -> {
            GameFactory.executeMove(game, 7);
        });
    }

    @Test
    public void executeMoveNoStonesInKalah() {
        when(mockGame.getBoardStatus().getPits()).thenReturn(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0));
        assertThrows(NoStonesInPitException.class, () -> {
            GameFactory.executeMove(mockGame, 1);
        });
    }

    @Test
    public void executeMoveWrongPlayerTurn() {
        assertThrows(WrongPlayerTurnException.class, () -> {
            GameFactory.executeMove(game, 1);
            game.setPlayerTurn("TWO");
            GameFactory.executeMove(game, 2);
        });
    }
}