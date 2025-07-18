package com.example.connectfour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the GameBoard class.
 */
public class GameBoardTest {
    
    private GameBoard gameBoard;
    
    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
    }
    
    /**
     * Test that the board is initialized correctly with empty cells.
     * This tests task 32: Test the game board initialization.
     */
    @Test
    void testBoardInitialization() {
        // Check that all cells are empty (represented by '.')
        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLUMNS; col++) {
                assertEquals('.', gameBoard.getSymbolAt(row, col), 
                        "Cell at row " + row + ", column " + col + " should be empty");
            }
        }
    }
    
    /**
     * Test that discs are placed correctly in columns and fall to the lowest available position.
     * This tests task 33: Test disc placement in columns.
     */
    @Test
    void testDiscPlacement() {
        // Place a disc in column 3 (0-based index)
        int row = gameBoard.placeDisc(3, 'X');
        
        // The disc should be placed at the bottom row (row 0)
        assertEquals(0, row, "Disc should be placed at the bottom row");
        assertEquals('X', gameBoard.getSymbolAt(0, 3), "Symbol 'X' should be at row 0, column 3");
        
        // Place another disc in the same column
        row = gameBoard.placeDisc(3, 'O');
        
        // The disc should be placed at row 1
        assertEquals(1, row, "Disc should be placed at row 1");
        assertEquals('O', gameBoard.getSymbolAt(1, 3), "Symbol 'O' should be at row 1, column 3");
        
        // Check that the first disc is still at row 0
        assertEquals('X', gameBoard.getSymbolAt(0, 3), "Symbol 'X' should still be at row 0, column 3");
    }
    
    /**
     * Test that placing a disc in a full column returns -1.
     */
    @Test
    void testPlacingDiscInFullColumn() {
        // Fill column 2 with discs
        for (int i = 0; i < GameBoard.ROWS; i++) {
            int row = gameBoard.placeDisc(2, 'X');
            assertEquals(i, row, "Disc should be placed at row " + i);
        }
        
        // Try to place another disc in the full column
        int row = gameBoard.placeDisc(2, 'O');
        assertEquals(-1, row, "Placing a disc in a full column should return -1");
    }
    
    /**
     * Test that isValidMove correctly identifies valid and invalid moves.
     */
    @Test
    void testIsValidMove() {
        // Initially all columns should be valid moves
        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            assertTrue(gameBoard.isValidMove(col), "Column " + col + " should be a valid move");
        }
        
        // Fill column 4 with discs
        for (int i = 0; i < GameBoard.ROWS; i++) {
            gameBoard.placeDisc(4, 'X');
        }
        
        // Column 4 should now be invalid
        assertFalse(gameBoard.isValidMove(4), "Column 4 should be an invalid move when full");
        
        // Out of bounds columns should be invalid
        assertFalse(gameBoard.isValidMove(-1), "Column -1 should be an invalid move");
        assertFalse(gameBoard.isValidMove(GameBoard.COLUMNS), "Column " + GameBoard.COLUMNS + " should be an invalid move");
    }
    
    /**
     * Test that the board correctly identifies when it is full.
     */
    @Test
    void testIsFull() {
        // Initially the board should not be full
        assertFalse(gameBoard.isFull(), "New board should not be full");
        
        // Fill the entire board
        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            for (int row = 0; row < GameBoard.ROWS; row++) {
                gameBoard.placeDisc(col, 'X');
            }
        }
        
        // Now the board should be full
        assertTrue(gameBoard.isFull(), "Board should be full after filling all cells");
    }
}