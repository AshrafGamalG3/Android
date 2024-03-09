package com.example.a2048game;

import java.util.Random;

public class GameLogic {

    public static final int BOARD_SIZE = 4;
    private static final int WINNING_TILE =2048 ;

    private int[][] gameBoard;
    private int score;
    private boolean gameWon;

    public GameLogic() {
        gameBoard = new int[BOARD_SIZE][BOARD_SIZE];
        score = 0;
        initializeBoard();
        addRandomTile();
        addRandomTile();
    }

    private void initializeBoard() {

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    gameBoard[i][j] = 0;
                }
            }

    }

    private void addRandomTile() {
        Random random = new Random();
        int value = random.nextInt(2) == 0 ? 2 : 4;

        int emptyCells = countEmptyCells();
        if (emptyCells > 0) {
            int position = random.nextInt(emptyCells);
            int count = 0;

            outerloop:
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (gameBoard[i][j] == 0) {
                        if (count == position) {
                            gameBoard[i][j] = value;
                            break outerloop;
                        }
                        count++;
                    }
                }
            }
        }
    }
    public void moveLeft() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Slide tiles to the left
            int[] row = gameBoard[i];
            row = slideTiles(row);

            // Merge adjacent tiles with the same value
            row = mergeTiles(row);

            // Slide tiles again after merging
            row = slideTiles(row);

            // Update the game board
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameBoard[i][j] = row[j];
            }
        }

        // Add a new random tile after the move
        addRandomTile();
    }

    public void moveRight() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            // Reverse the row, slide left, merge, slide left again, then reverse back
            int[] reversedRow = reverseArray(gameBoard[i]);
            reversedRow = slideTiles(reversedRow);
            reversedRow = mergeTiles(reversedRow);
            reversedRow = slideTiles(reversedRow);
            reversedRow = reverseArray(reversedRow);

            // Update the game board
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameBoard[i][j] = reversedRow[j];
            }
        }

        // Add a new random tile after the move
        addRandomTile();
    }
    public void moveUp() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            // Extract a column, slide left, merge, slide left again, then insert back
            int[] column = getColumn(j);
            column = slideTiles(column);
            column = mergeTiles(column);
            column = slideTiles(column);

            // Update the game board
            for (int i = 0; i < BOARD_SIZE; i++) {
                gameBoard[i][j] = column[i];
            }
        }

        // Add a new random tile after the move
        addRandomTile();
    }


    public void moveDown() {
        for (int j = 0; j < BOARD_SIZE; j++) {
            // Extract a column, reverse, slide left, merge, slide left again, reverse back, then insert back
            int[] reversedColumn = reverseArray(getColumn(j));
            reversedColumn = slideTiles(reversedColumn);
            reversedColumn = mergeTiles(reversedColumn);
            reversedColumn = slideTiles(reversedColumn);
            reversedColumn = reverseArray(reversedColumn);

            // Update the game board
            for (int i = 0; i < BOARD_SIZE; i++) {
                gameBoard[i][j] = reversedColumn[i];
            }
        }

        // Add a new random tile after the move
        addRandomTile();
    }


    private int countEmptyCells() {
        int count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    count++;
                }
            }
        }
        return count;
    }



    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getScore() {
        return score;
    }


    private int[] slideTiles(int[] array) {
        int[] result = new int[array.length];
        int position = 0;

        for (int value : array) {
            if (value != 0) {
                result[position] = value;
                position++;
            }
        }

        return result;
    }


    private int[] mergeTiles(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] != 0 && array[i] == array[i + 1]) {
                // Merge adjacent tiles with the same value
                array[i] *= 2;
                array[i + 1] = 0;
                score += array[i];
            }
        }

        return array;
    }

    private int[] reverseArray(int[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[array.length - i - 1];
        }
        return result;
    }

    private int[] getColumn(int columnIndex) {
        int[] column = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            column[i] = gameBoard[i][columnIndex];
        }
        return column;
    }

    public boolean isGameOver() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    return false; // Game is not over if there is an empty tile
                }

                // Check adjacent tiles for possible merges
                if (j < BOARD_SIZE - 1 && gameBoard[i][j] == gameBoard[i][j + 1]) {
                    return false; // Game is not over if there are adjacent equal tiles
                }

                if (i < BOARD_SIZE - 1 && gameBoard[i][j] == gameBoard[i + 1][j]) {
                    return false; // Game is not over if there are adjacent equal tiles
                }
            }
        }
        return true; // Game is over if no empty tiles or adjacent equal tiles are found
    }
    public boolean isGameWon() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (gameBoard[i][j] == WINNING_TILE) {
                    return true; // Game is won if any tile has reached the winning value
                }
            }
        }
        return false; // Game is not won if no tile has reached the winning value
    }
    public void resetGame() {
        // Initialize the game board and score
        gameBoard = new int[BOARD_SIZE][BOARD_SIZE];
        score = 0;

        // Place initial tiles
     addRandomTile();
     addRandomTile();
    }
}
