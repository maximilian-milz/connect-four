package com.example.connectfour;

/**
 * Represents the game board for Connect Four.
 * The board is a 7x6 grid (7 columns, 6 rows).
 * The bottom row is row 0, and the leftmost column is column 0.
 */
public class GameBoard {
    // Constants for board dimensions
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;

    // Empty cell character
    private static final char EMPTY_CELL = '.';

    // The board grid
    private final char[][] grid;

    /**
     * Creates a new empty game board.
     */
    public GameBoard() {
        grid = new char[ROWS][COLUMNS];
        initializeBoard();
    }

    /**
     * Initializes the board with empty cells.
     */
    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                grid[row][col] = EMPTY_CELL;
            }
        }
    }

    /**
     * Checks if a column is valid and not full.
     * 
     * @param column The column to check (0-based index)
     * @return true if the column is valid and not full, false otherwise
     */
    public boolean isValidMove(int column) {
        return column >= 0 && column < COLUMNS && grid[ROWS - 1][column] == EMPTY_CELL;
    }

    /**
     * Places a disc in the specified column.
     * The disc will fall to the lowest empty position in the column.
     * 
     * @param column The column to place the disc (0-based index)
     * @param symbol The symbol representing the player's disc
     * @return The row where the disc was placed, or -1 if the column is full
     */
    public int placeDisc(int column, char symbol) {
        if (!isValidMove(column)) {
            return -1;
        }

        // Find the lowest empty row in the column
        for (int row = 0; row < ROWS; row++) {
            if (grid[row][column] == EMPTY_CELL) {
                grid[row][column] = symbol;
                return row;
            }
        }

        return -1; // This should never happen if isValidMove is called first
    }

    /**
     * Gets the symbol at the specified position.
     * 
     * @param row The row (0-based index)
     * @param column The column (0-based index)
     * @return The symbol at the position, or EMPTY_CELL if the position is empty
     */
    public char getSymbolAt(int row, int column) {
        if (row >= 0 && row < ROWS && column >= 0 && column < COLUMNS) {
            return grid[row][column];
        }
        return EMPTY_CELL;
    }

    /**
     * Checks if the board is full (no more valid moves).
     * 
     * @return true if the board is full, false otherwise
     */
    public boolean isFull() {
        for (int col = 0; col < COLUMNS; col++) {
            if (grid[ROWS - 1][col] == EMPTY_CELL) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the current state of the board to the console.
     */
    public void printBoard() {
        System.out.println("Current board:");

        // Print the board from top to bottom
        for (int row = ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }

        // Print column numbers
        System.out.println("1 2 3 4 5 6 7");
        System.out.println();
    }

    /**
     * Resets the board to its initial empty state.
     */
    public void reset() {
        initializeBoard();
    }
}
