package edu.hw1;

public class Task8 {

    private static final int BOARD_SIZE = 8;
    private static final int EMPTY = 0;
    private static final int KNIGHT = 1;

    private static final int SINGLE_STEP = 1;
    private static final int DOUBLE_STEP = 2;

    /**
     * Checks if the knights on a given 8x8 board are positioned such that
     * they cannot capture each other.
     *
     * @param board 8x8 array representing the board; 0 indicates an empty square, 1 indicates a knight.
     * @return true if knights are safely positioned, false otherwise.
     */
    public static boolean knightBoardCapture(int[][] board) throws IllegalArgumentException {
        if (board.length != BOARD_SIZE || board[0].length != BOARD_SIZE) {
            throw new IllegalArgumentException("Board must be 8x8");
        }

        int[][] moves = {
            {-DOUBLE_STEP, -SINGLE_STEP}, {-DOUBLE_STEP, SINGLE_STEP},
            {-SINGLE_STEP, -DOUBLE_STEP}, {-SINGLE_STEP, DOUBLE_STEP},
            {SINGLE_STEP, -DOUBLE_STEP}, {SINGLE_STEP, DOUBLE_STEP},
            {DOUBLE_STEP, -SINGLE_STEP}, {DOUBLE_STEP, SINGLE_STEP}
        };

        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] == KNIGHT) {
                    for (int[] move : moves) {
                        int newX = i + move[0];
                        int newY = j + move[1];
                        if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE) {
                            if (board[newX][newY] == KNIGHT) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private Task8() {}
}
