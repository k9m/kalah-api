package org.k9m.kalah.api.service.board;

import org.k9m.kalah.api.exception.InvalidPitExceptionException;
import org.k9m.kalah.api.exception.NoStonesInPitException;
import org.k9m.kalah.api.exception.WrongPlayerTurnException;
import org.k9m.kalah.persistence.model.BoardStatus;
import org.k9m.kalah.persistence.model.Game;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.k9m.kalah.api.service.board.KalahBoard.Player;
import static org.k9m.kalah.api.service.board.KalahBoard.Status.ACTIVE;

@Component
public class GameManager {

    public Game createGame(){
        return new Game()
                .setGameStatus(ACTIVE.toString())
                .setBoardStatus(new BoardStatus().setPits(boardToPits(new KalahBoard())));
    }

    public Game executeMove(final Game game, final Integer pitNumber){
        final KalahBoard board = new KalahBoard(game.getBoardStatus().getPits().stream().mapToInt(i->i).toArray());
        final Player requestPlayer = board.playerFromPitNumber(pitNumber);

        Player playerTurn = game.getPlayerTurn() == null ? requestPlayer : Player.valueOf(game.getPlayerTurn());
        if(requestPlayer == playerTurn){
            if(board.isPitAKalah(pitNumber)){
                throw new InvalidPitExceptionException("This pit[" + pitNumber + "] is a Kalah, cannot make a move from here");
            }
            else if(board.isPitEmpty(pitNumber)){
                throw new NoStonesInPitException("There aren't any stones in pit: " + pitNumber);
            }
            else{
                final boolean isAnotherMove = board.executeMove(pitNumber);
                if(!isAnotherMove){
                    playerTurn = requestPlayer == Player.ONE ? Player.TWO : Player.ONE;
                }

                return new Game()
                        .setGameId(game.getGameId())
                        .setPlayerTurn(playerTurn.toString())
                        .setGameStatus(board.status().toString())
                        .setBoardStatus(new BoardStatus().setPits(boardToPits(board)));
            }
        }
        else{
            throw new WrongPlayerTurnException("Wrong player's turn, current player is: " + playerTurn);
        }
    }

    private static List<Integer> boardToPits(final KalahBoard board){
        return Arrays.stream(board.getBoard()).boxed().collect(Collectors.toList());
    }



}
