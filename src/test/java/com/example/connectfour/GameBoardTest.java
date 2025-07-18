package com.example.connectfour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    private GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
    }

    @Test
    void testBoardInitialization() {
        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLUMNS; col++) {
                assertEquals('.', gameBoard.getSymbolAt(row, col), 
                        "Cell at row " + row + ", column " + col + " should be empty");
            }
        }
    }

    @Test
    void testDiscPlacement() {
        int row = gameBoard.placeDisc(3, 'X');

        assertEquals(0, row, "Disc should be placed at the bottom row");
        assertEquals('X', gameBoard.getSymbolAt(0, 3), "Symbol 'X' should be at row 0, column 3");

        row = gameBoard.placeDisc(3, 'O');

        assertEquals(1, row, "Disc should be placed at row 1");
        assertEquals('O', gameBoard.getSymbolAt(1, 3), "Symbol 'O' should be at row 1, column 3");

        assertEquals('X', gameBoard.getSymbolAt(0, 3), "Symbol 'X' should still be at row 0, column 3");
    }

    @Test
    void testPlacingDiscInFullColumn() {
        for (int i = 0; i < GameBoard.ROWS; i++) {
            int row = gameBoard.placeDisc(2, 'X');
            assertEquals(i, row, "Disc should be placed at row " + i);
        }

        int row = gameBoard.placeDisc(2, 'O');
        assertEquals(-1, row, "Placing a disc in a full column should return -1");
    }

    @Test
    void testIsValidMove() {
        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            assertTrue(gameBoard.isValidMove(col), "Column " + col + " should be a valid move");
        }

        for (int i = 0; i < GameBoard.ROWS; i++) {
            gameBoard.placeDisc(4, 'X');
        }

        assertFalse(gameBoard.isValidMove(4), "Column 4 should be an invalid move when full");

        assertFalse(gameBoard.isValidMove(-1), "Column -1 should be an invalid move");
        assertFalse(gameBoard.isValidMove(GameBoard.COLUMNS), "Column " + GameBoard.COLUMNS + " should be an invalid move");
    }

    @Test
    void testIsFull() {
        assertFalse(gameBoard.isFull(), "New board should not be full");

        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            for (int row = 0; row < GameBoard.ROWS; row++) {
                gameBoard.placeDisc(col, 'X');
            }
        }

        assertTrue(gameBoard.isFull(), "Board should be full after filling all cells");
    }
}
