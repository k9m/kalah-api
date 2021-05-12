package org.k9m.kalah.service.game;

import org.k9m.kalah.service.game.exception.InvalidPitExceptionException;
import org.k9m.kalah.service.game.exception.NoStonesInPitException;
import org.k9m.kalah.service.game.exception.WrongPlayerTurnException;
import org.k9m.kalah.persistence.model.BoardStatus;
import org.k9m.kalah.persistence.model.Game;

import static org.k9m.kalah.service.game.KalahBoard.Status.ACTIVE;
import static org.k9m.kalah.service.game.KalahBoard.Player;

public class GameFactory {

    public static Game createGame(){
        return new Game()
            .setGameStatus(ACTIVE.toString())
            .setBoardStatus(new BoardStatus().setPits(new KalahBoard().boardToPits()));
    }

    public static Game executeMove(final Game game, final Integer pitNumber){
        final KalahBoard board = new KalahBoard(game.getBoardStatus().getPits().stream().mapToInt(i->i).toArray());
        final KalahBoard.Player currentPlayer = board.playerFromPitNumber(pitNumber);

        Player playerTurn = game.getPlayerTurn() == null ? currentPlayer : Player.valueOf(game.getPlayerTurn());
        if(currentPlayer == playerTurn){
            if(board.isPitAKalah(pitNumber)){
                throw new InvalidPitExceptionException("This pit[" + pitNumber + "] is a Kalah, cannot make a move from here");
            }
            else if(board.isPitEmpty(pitNumber)){
                throw new NoStonesInPitException("There aren't any stones in pit: " + pitNumber);
            }
            else{
                final boolean isAnotherMove = board.executeMove(pitNumber);
                if(!isAnotherMove){
                    playerTurn = currentPlayer.nextPlayer();
                }

                return new Game()
                    .setGameId(game.getGameId())
                    .setPlayerTurn(playerTurn.toString())
                    .setGameStatus(board.status().toString())
                    .setBoardStatus(
                        new BoardStatus()
                            .setPits(board.boardToPits()));
            }
        }
        else{
            throw new WrongPlayerTurnException("Wrong player's turn, current player is: " + playerTurn);
        }
    }



}
