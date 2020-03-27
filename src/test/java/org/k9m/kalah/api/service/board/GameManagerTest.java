package org.k9m.kalah.api.service.board;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.k9m.kalah.api.exception.InvalidPitExceptionException;
import org.k9m.kalah.api.exception.NoStonesInPitException;
import org.k9m.kalah.api.exception.WrongPlayerTurnException;
import org.k9m.kalah.persistence.model.Game;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameManagerTest {

    private GameManager gameManager;
    private Game game;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Game mockGame;



    @Before
    public void setUp() {
        gameManager = new GameManager();
        game = gameManager.createGame();
    }

    @Test
    public void createGame() {
        assertThat("Number of pits are 14", gameManager.createGame().getBoardStatus().getPits().size(), is(14));
    }

    @Test(expected = InvalidPitExceptionException.class)
    public void executeMoveFromKalah() {
        gameManager.executeMove(game, 7);
    }

    @Test(expected = NoStonesInPitException.class)
    public void executeMoveNoStonesInKalah() {
        when(mockGame.getBoardStatus().getPits()).thenReturn(Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0));
        gameManager.executeMove(mockGame, 1);
    }

    @Test(expected = WrongPlayerTurnException.class)
    public void executeMoveWrongPlayerTurn() {
        gameManager.executeMove(game, 1);
        game.setPlayerTurn("TWO");
        gameManager.executeMove(game, 2);
    }
}