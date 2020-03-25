package org.k9m.kalah.api.service;

import lombok.Data;
import org.k9m.kalah.api.exception.InvalidPitExceptionException;
import org.k9m.kalah.api.exception.NoStonesInPitException;

import java.util.Map;

import static org.k9m.kalah.api.service.KalahBoard.Player;

@Data
class GameManager {

    private final String gameId;
    private final KalahBoard board;

    private Player playerTurn = null;

    Map<String, String> executeMove(final Integer pitNumber){
        final Player requestPlayer = board.playerFromPitNumber(pitNumber);

        if(playerTurn == null || requestPlayer == playerTurn){
            if(board.isPitAKalah(pitNumber)){
                throw new InvalidPitExceptionException("This pit[" + pitNumber + "] is a Kalah, cannot make a move from here");
            }
            if(board.isPitEmpty(pitNumber)){
                throw new NoStonesInPitException("There aren't any stones in pit: " + pitNumber);
            }
            else{
                final boolean isAnotherMove = board.executeMove(pitNumber);
                if(!isAnotherMove){
                    playerTurn = requestPlayer == Player.ONE ? Player.TWO : Player.ONE;
                }

                return board.getStatus();
            }
        }
        else{
            throw new NoStonesInPitException("Wrong player's turn, current player is: " + playerTurn);
        }

    }

}
