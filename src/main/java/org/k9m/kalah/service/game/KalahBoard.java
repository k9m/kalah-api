package org.k9m.kalah.service.game;

import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.k9m.kalah.service.game.KalahBoard.Status.*;

@ToString(onlyExplicitlyIncluded = true)
class KalahBoard {

    enum Player{
        ONE(KALAH_1, KALAH_2),
        TWO(KALAH_2, KALAH_1);

        private final int ownKalah;
        private final int otherKalah;

        Player(int ownKalah, int otherKalah) {
            this.ownKalah = ownKalah;
            this.otherKalah = otherKalah;
        }

        public Player nextPlayer(){
            return this == Player.ONE ? Player.TWO : Player.ONE;
        }
    }

    enum Status{
        ACTIVE,
        PLAYER_ONE_WON,
        PLAYER_TWO_WON,
        DRAW
    }

    public static final int NR_PITS = 6;
    public static final int NR_STARTING_STONES = 6;

    private static final int KALAH_1 = NR_PITS + 1;
    private static final int KALAH_2 = 2 * NR_PITS + 2;

    @ToString.Include
    private final int[] board;

    KalahBoard() {
        board = new int[2 * NR_PITS + 2];
        for (int i = 0; i < NR_PITS; i++) {
            board[i] = NR_STARTING_STONES;
            board[i + NR_PITS + 1] = NR_STARTING_STONES;
        }
    }

    KalahBoard(final int[] board) {
        this.board = board;
    }

    boolean isPitEmpty(final int pitNumber) {
        return board[pitNumber - 1] == 0;
    }

    boolean isPitAKalah(final int pitNumber) {
        return pitNumber == KALAH_1 || pitNumber == KALAH_2;
    }

    int nrStonesInPit(final int pitNumber) {
        return board[pitNumber - 1];
    }

    boolean executeMove(final int pitNumber) {
        final int pitIndex = pitNumber - 1;
        final Player player = this.playerFromPitNumber(pitNumber);
        int stonesInPit = board[pitIndex];
        board[pitIndex] = 0;

        int nextPitIndex = pitNumber;
        while (stonesInPit > 0) {
            if (nextPitIndex > KALAH_2 - 1) {
                nextPitIndex = 0;
            }

            if(nextPitIndex != player.otherKalah - 1){
                stonesInPit--;
                board[nextPitIndex]++;
            }

            nextPitIndex++;
        }

        final boolean isAnotherMove;
        if(nextPitIndex == player.ownKalah){
            isAnotherMove = true;
        }
        else{
            this.captureStonesFromOppositePitIfPitEmpty(player, nextPitIndex - 1);
            isAnotherMove = false;
        }

        return isAnotherMove;
    }

    private void captureStonesFromOppositePitIfPitEmpty(final Player player, final int pitIndex){
        if(board[pitIndex] == 1) {
            final int oppositeIndex = board.length - pitIndex - 2;
            board[player.ownKalah - 1] += 1 + board[oppositeIndex];
            board[pitIndex] = 0;
            board[oppositeIndex] = 0;
        }
    }

    Status status() {
        boolean isPlayerOneEmpty = true;
        boolean isPlayerTwoEmpty = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i] > 0) {
                if (i < NR_PITS) {
                    isPlayerOneEmpty = false;
                }
                else if (NR_PITS < i  && i <= 2 * NR_PITS) {
                    isPlayerTwoEmpty = false;
                }
            }
        }

        final Status status;
        if(!isPlayerOneEmpty && !isPlayerTwoEmpty){ status = ACTIVE; }
        else if(isPlayerOneEmpty && !isPlayerTwoEmpty){ status = PLAYER_ONE_WON; }
        else if(!isPlayerOneEmpty && isPlayerTwoEmpty){ status = PLAYER_TWO_WON; }
        else{ status = DRAW; }

        return status;
    }

    boolean isPlayerAllowed(final Player player, final int pitNumber){
        if(player == Player.ONE){
            return pitNumber < NR_PITS;
        }
        else{
            return KALAH_1 < pitNumber && pitNumber < KALAH_2;
        }
    }

    public Player playerFromPitNumber(final int pitNumber){
        return pitNumber < KALAH_1 ? Player.ONE : Player.TWO;
    }

    public List<Integer> boardToPits(){
        return Arrays.stream(board).boxed().collect(Collectors.toList());
    }


}
