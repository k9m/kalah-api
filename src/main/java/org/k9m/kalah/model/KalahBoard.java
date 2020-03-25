package org.k9m.kalah.model;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString(onlyExplicitlyIncluded = true)
public class KalahBoard {

    public static final int NR_PITS = 6;
    public static final int NR_STARTING_STONES = 6;

    public static final int KALAH_1 = NR_PITS + 1;
    public static final int KALAH_2 = 2 * NR_PITS + 2;

    @ToString.Include
    private final int[] board = new int[2 * NR_PITS + 2];

    public KalahBoard() {
        for (int i = 0; i < NR_PITS; i++) {
            board[i] = NR_STARTING_STONES;
            board[i + NR_PITS + 1] = NR_STARTING_STONES;
        }
    }

    public boolean isPitEmpty(final int pitNumber) {
        return board[pitNumber - 1] == 0;
    }

    public int nrStonesInPit(final int pitNumber) {
        return board[pitNumber - 1];
    }

    public boolean executeMove(final int pitNumber) {
        final int stonesInPit = board[pitNumber - 1];
        board[pitNumber - 1] = 0;

        final boolean isAnotherMove;
        if (pitNumber < KALAH_1) {
            isAnotherMove = pitNumber + stonesInPit == KALAH_1;
        } else {
            isAnotherMove = pitNumber + stonesInPit == KALAH_2;
        }

        for (int i = 0; i < stonesInPit; i++) {
            board[pitNumber + i] = ++board[pitNumber + i];
        }

        return isAnotherMove;
    }

    public Map<String, String> getStatus(){
        final Map<String, String> status = new HashMap<>();

        int index = 1;
        for(int stones : board){
            status.put(String.valueOf(index++), String.valueOf(stones));
        }

        return status;
    }


    public boolean isGameOver() {
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


}
