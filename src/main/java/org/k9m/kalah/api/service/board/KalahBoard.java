package org.k9m.kalah.api.service.board;

import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@ToString(onlyExplicitlyIncluded = true)
public class KalahBoard {

    public enum Player{
        ONE(KALAH_1, KALAH_2),
        TWO(KALAH_2, KALAH_1);

        private int ownKalah;
        private int otherKalah;

        Player(int ownKalah, int otherKalah) {
            this.ownKalah = ownKalah;
            this.otherKalah = otherKalah;
        }
    }

    public static final int NR_PITS = 6;
    public static final int NR_STARTING_STONES = 6;

    private static final int KALAH_1 = NR_PITS + 1;
    private static final int KALAH_2 = 2 * NR_PITS + 2;

    @ToString.Include
    private final int[] board = new int[2 * NR_PITS + 2];

    KalahBoard() {
        for (int i = 0; i < NR_PITS; i++) {
            board[i] = NR_STARTING_STONES;
            board[i + NR_PITS + 1] = NR_STARTING_STONES;
        }
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

        if(nextPitIndex == player.ownKalah){
            return true;
        }
        else{
            this.captureStonesFromOppositePitIfPitEmpty(player, nextPitIndex - 1);
            return false;
        }
    }

    private void captureStonesFromOppositePitIfPitEmpty(final Player player, final int pitIndex){
        if(board[pitIndex] == 1) {
            final int oppositeIndex = board.length - pitIndex - 2;
            board[player.ownKalah - 1] += 1 + board[oppositeIndex];
            board[pitIndex] = 0;
            board[oppositeIndex] = 0;
        }
    }

    Map<String, String> getStatus(){
        final Map<String, String> status = new LinkedHashMap<>();

        int index = 1;
        for(int stones : board){
            status.put(String.valueOf(index++), String.valueOf(stones));
        }

        return status;
    }


    boolean isGameOver() {
        boolean isPlayerOneEmpty = true;
        boolean isPlayerTwoEmpty = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i] > 0) {
                if (i < NR_PITS) {
                    isPlayerOneEmpty = false;
                }
                else if (NR_PITS < i  && i < 2 * NR_PITS) {
                    isPlayerTwoEmpty = false;
                }
            }
        }

        return isPlayerOneEmpty || isPlayerTwoEmpty;
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


}