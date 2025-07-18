package com.example.connectfour;

import java.util.Scanner;

/**
 * Controls the game flow and logic for Connect Four.
 * Handles player turns, input validation, and win/draw condition checks.
 */
public class GameLogic {
    private final GameBoard board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final Scanner scanner;
    private final GameStats gameStats;

    /**
     * Creates a new game logic controller.
     * 
     * @param board The game board
     * @param player1 The first player
     * @param player2 The second player
     */
    public GameLogic(GameBoard board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.scanner = new Scanner(System.in);
        this.gameStats = new GameStats();
    }

    /**
     * Starts the game and runs the main game loop.
     */
    public void startGame() {
        boolean playAgain = true;

        while (playAgain) {
            if (board.isFull() || hasDiscs()) {
                board.reset();
            }

            currentPlayer = player1;

            board.printBoard();

            boolean gameOver = false;

            while (!gameOver) {
                int column = getPlayerMove();

                int row = board.placeDisc(column, currentPlayer.symbol());

                board.printBoard();

                if (checkWin(row, column)) {
                    System.out.println(currentPlayer.name() + " wins!");
                    gameStats.recordWin(currentPlayer.name());
                    gameOver = true;
                } 
                else if (board.isFull()) {
                    System.out.println("The game is a draw!");
                    gameStats.recordDraw();
                    gameOver = true;
                } 
                else {
                    switchPlayer();
                }
            }

            gameStats.printStats();

            playAgain = askPlayAgain();
        }

        scanner.close();
    }

    /**
     * Asks players if they want to play again.
     * 
     * @return true if players want to play again, false otherwise
     */
    private boolean askPlayAgain() {
        System.out.println("Do you want to play again? (y/n):");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                System.out.println("Thanks for playing!");
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no:");
            }
        }
    }

    /**
     * Checks if the board has any discs placed.
     * 
     * @return true if there are any discs on the board, false if the board is empty
     */
    private boolean hasDiscs() {
        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLUMNS; col++) {
                if (board.getSymbolAt(row, col) != '.') {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the player's move (column selection).
     * 
     * @return The selected column (0-based index)
     */
    private int getPlayerMove() {
        int column = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.println(currentPlayer.name() + " (" + currentPlayer.symbol() + "), choose a column (1-7):");

            try {
                String input = scanner.nextLine().trim();
                int userColumn = Integer.parseInt(input);

                // Convert from 1-based (user input) to 0-based (internal representation)
                column = userColumn - 1;

                if (board.isValidMove(column)) {
                    validInput = true;
                } else {
                    System.out.println("Invalid move. The column is either full or out of bounds. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
            }
        }

        return column;
    }

    /**
     * Switches the current player.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Checks if the current player has won after placing a disc.
     * 
     * @param row The row where the disc was placed
     * @param column The column where the disc was placed
     * @return true if the current player has won, false otherwise
     */
    private boolean checkWin(int row, int column) {
        char symbol = currentPlayer.symbol();

        if (checkHorizontal(row, symbol)) {
            return true;
        }

        if (checkVertical(column, symbol)) {
            return true;
        }

        return checkDiagonal(row, column, symbol);
    }

    /**
     * Checks for a horizontal win.
     * 
     * @param row The row to check
     * @param symbol The player's symbol
     * @return true if there are 4 connected symbols horizontally
     */
    private boolean checkHorizontal(int row, char symbol) {
        int count = 0;

        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            if (board.getSymbolAt(row, col) == symbol) {
                count++;
                if (count >= 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }

    /**
     * Checks for a vertical win.
     * 
     * @param column The column to check
     * @param symbol The player's symbol
     * @return true if there are 4 connected symbols vertically
     */
    private boolean checkVertical(int column, char symbol) {
        int count = 0;

        for (int row = 0; row < GameBoard.ROWS; row++) {
            if (board.getSymbolAt(row, column) == symbol) {
                count++;
                if (count >= 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        return false;
    }

    /**
     * Checks for a diagonal win (both directions).
     * 
     * @param row The row where the disc was placed
     * @param column The column where the disc was placed
     * @param symbol The player's symbol
     * @return true if there are 4 connected symbols diagonally
     */
    private boolean checkDiagonal(int row, int column, char symbol) {
        // Check diagonal (bottom-left to top-right)
        if (checkDiagonalDirection(row, column, -1, 1, symbol) || 
            checkDiagonalDirection(row, column, 1, -1, symbol)) {
            return true;
        }

        // Check diagonal (bottom-right to top-left)
        return checkDiagonalDirection(row, column, -1, -1, symbol) || 
               checkDiagonalDirection(row, column, 1, 1, symbol);
    }

    /**
     * Checks for a win in a specific diagonal direction.
     * 
     * @param row The starting row
     * @param column The starting column
     * @param rowDelta The row direction (-1, 0, or 1)
     * @param colDelta The column direction (-1, 0, or 1)
     * @param symbol The player's symbol
     * @return true if there are 4 connected symbols in the specified direction
     */
    private boolean checkDiagonalDirection(int row, int column, int rowDelta, int colDelta, char symbol) {
        int count = 1; // Start with 1 for the current position

        // Check in one direction
        int r = row + rowDelta;
        int c = column + colDelta;

        while (r >= 0 && r < GameBoard.ROWS && c >= 0 && c < GameBoard.COLUMNS && 
               board.getSymbolAt(r, c) == symbol) {
            count++;
            r += rowDelta;
            c += colDelta;
        }

        // Check in the opposite direction
        r = row - rowDelta;
        c = column - colDelta;

        while (r >= 0 && r < GameBoard.ROWS && c >= 0 && c < GameBoard.COLUMNS && 
               board.getSymbolAt(r, c) == symbol) {
            count++;
            r -= rowDelta;
            c -= colDelta;
        }

        return count >= 4;
    }
}
