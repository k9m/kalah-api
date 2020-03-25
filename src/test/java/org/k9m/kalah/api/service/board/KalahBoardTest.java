package org.k9m.kalah.api.service.board;


import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class KalahBoardTest {

    private KalahBoard kalahBoard;

    @Before
    public void before(){
        kalahBoard = new KalahBoard();
        log.info(kalahBoard.toString());
    }

    @After
    public void after(){
        log.info(kalahBoard.toString());
    }

    @Test
    public void isPitEmpty() {
        assertThat("Kalah 1 should be empty", kalahBoard.isPitEmpty(7), is(true));
        assertThat("Pit 1 should not be empty", kalahBoard.isPitEmpty(1), is(false));
    }

    @Test
    public void nrStonesInPit() {
        assertThat("Pit 1 should have", kalahBoard.nrStonesInPit(1), is(6));
        assertThat("Kalah 1 should be empty", kalahBoard.nrStonesInPit(7), is(0));
    }

    @Test
    public void executeMove() {
        boolean isAnotherRound = kalahBoard.executeMove(1);

        assertThat("Pit 1 should be empty", kalahBoard.isPitEmpty(1), is(true));
        assertThat("Pit 2 should have", kalahBoard.nrStonesInPit(2), is(7));
        assertThat("Pit 6 should have", kalahBoard.nrStonesInPit(6), is(7));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(1));
        assertThat("There should be one more round", isAnotherRound, is(true));

        boolean isOneMoreRound = kalahBoard.executeMove(6);

        assertThat("Pit 6 should be empty", kalahBoard.isPitEmpty(6), is(true));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(2));
        assertThat("Pit 8 should have", kalahBoard.nrStonesInPit(8), is(7));
        assertThat("Pit 13 should have", kalahBoard.nrStonesInPit(13), is(7));
        assertThat("There shouldn't be one more round", isOneMoreRound, is(false));
    }

    @Test
    public void executeMoveOverlap() {
        boolean isAnotherRound = kalahBoard.executeMove(12);

        assertThat("Pit 12 should be empty", kalahBoard.isPitEmpty(12), is(true));
        assertThat("Pit 13 should have", kalahBoard.nrStonesInPit(13), is(7));
        assertThat("Pit 14 should have", kalahBoard.nrStonesInPit(14), is(1));
        assertThat("Pit 1 should have", kalahBoard.nrStonesInPit(1), is(7));
        assertThat("Pit 5 should have", kalahBoard.nrStonesInPit(5), is(6));
        assertThat("There shouldn't be one more round", isAnotherRound, is(false));

    }

    @Test
    public void executeMoveCaptureOpposite() {
        kalahBoard.executeMove(1);

        assertThat("Pit 1 should be empty", kalahBoard.isPitEmpty(1), is(true));
        assertThat("Pit 2 should have", kalahBoard.nrStonesInPit(2), is(7));
        assertThat("Pit 6 should have", kalahBoard.nrStonesInPit(6), is(7));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(1));
        log.info(kalahBoard.toString());

        kalahBoard.executeMove(6);

        assertThat("Pit 6 should be empty", kalahBoard.isPitEmpty(6), is(true));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(2));
        assertThat("Pit 8 should have", kalahBoard.nrStonesInPit(8), is(7));
        assertThat("Pit 13 should have", kalahBoard.nrStonesInPit(13), is(7));
        log.info(kalahBoard.toString());

        kalahBoard.executeMove(13);

        assertThat("Pit 13 should be empty", kalahBoard.isPitEmpty(13), is(true));
        assertThat("Pit 1 should have", kalahBoard.nrStonesInPit(1), is(1));
        assertThat("Pit 2 should have", kalahBoard.nrStonesInPit(2), is(8));
        assertThat("Pit 5 should have", kalahBoard.nrStonesInPit(5), is(8));

        assertThat("Pit 6 should have", kalahBoard.nrStonesInPit(6), is(0));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(2));
        assertThat("Pit 8 should have", kalahBoard.nrStonesInPit(8), is(0));
        assertThat("Pit 14 should have", kalahBoard.nrStonesInPit(14), is(9));
        log.info(kalahBoard.toString());
    }
    


    @Test
    public void executeMoveSkipOtherKala() {
        
        executeMoveCaptureOpposite();

        kalahBoard.executeMove(5);

        assertThat("Pit 5 should be empty", kalahBoard.isPitEmpty(5), is(true));
        assertThat("Pit 3 should have", kalahBoard.nrStonesInPit(3), is(8));
        assertThat("Pit 6 should have", kalahBoard.nrStonesInPit(6), is(1));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(5));
        assertThat("Pit 8 should have", kalahBoard.nrStonesInPit(8), is(1));
        assertThat("Pit 10 should have", kalahBoard.nrStonesInPit(10), is(8));
        log.info(kalahBoard.toString());

        kalahBoard.executeMove(8);

        assertThat("Pit 8 should be empty", kalahBoard.isPitEmpty(8), is(true));
        assertThat("Pit 2 should have", kalahBoard.nrStonesInPit(2), is(8));
        assertThat("Pit 3 should have", kalahBoard.nrStonesInPit(3), is(8));
        assertThat("Pit 6 should have", kalahBoard.nrStonesInPit(6), is(1));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(5));
        assertThat("Pit 14 should have", kalahBoard.nrStonesInPit(14), is(9));
        log.info(kalahBoard.toString());

        kalahBoard.executeMove(12);

        assertThat("Pit 12 should be empty", kalahBoard.isPitEmpty(12), is(true));
        assertThat("Pit 2 should have", kalahBoard.nrStonesInPit(2), is(9));
        assertThat("Pit 3 should have", kalahBoard.nrStonesInPit(3), is(9));
        assertThat("Pit 6 should have", kalahBoard.nrStonesInPit(6), is(2));
        assertThat("Pit 7 should have", kalahBoard.nrStonesInPit(7), is(5));
        assertThat("Pit 14 should have", kalahBoard.nrStonesInPit(14), is(10));
        log.info(kalahBoard.toString());


    }

    @Test
    public void getStatus() {
        boolean isAnotherRound = kalahBoard.executeMove(1);
        Map<String, String> status = kalahBoard.getStatus();

        assertThat("Pit 1 should be empty", status.get("1"), is("0"));
        assertThat("Pit 2 should have", status.get("2"), is("7"));
    }

    @Test
    public void testGameOver(){
        //TODO
        boolean isGameOver = kalahBoard.isGameOver();
    }

    @Test
    public void isPlayerAllowed() {
        assertThat("Player One can't access her own Kalah", kalahBoard.isPlayerAllowed(KalahBoard.Player.ONE, 7), is(false));
        assertThat("Player Two can't access her own Kalah", kalahBoard.isPlayerAllowed(KalahBoard.Player.TWO, 14), is(false));

        assertThat("Player One can't access Plater Two's Kalah", kalahBoard.isPlayerAllowed(KalahBoard.Player.ONE, 8), is(false));
        assertThat("Player One can't access Plater Two's Kalah", kalahBoard.isPlayerAllowed(KalahBoard.Player.ONE, 13), is(false));

        assertThat("Player TWO can't access Plater One's Kalah", kalahBoard.isPlayerAllowed(KalahBoard.Player.TWO, 1), is(false));
        assertThat("Player TWO can't access Plater One's Kalah", kalahBoard.isPlayerAllowed(KalahBoard.Player.TWO, 6), is(false));
    }
}