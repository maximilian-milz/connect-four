package com.example.connectfour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

class GameLogicTest {

    private GameBoard gameBoard;
    private Player player1;
    private Player player2;
    private GameLogic gameLogic;

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
        player1 = new Player("Player 1", 'X');
        player2 = new Player("Player 2", 'O');

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testHorizontalWinCondition() {
        gameLogic = new GameLogic(gameBoard, player1, player2);

        gameBoard.placeDisc(0, player1.symbol());
        gameBoard.placeDisc(1, player1.symbol());
        gameBoard.placeDisc(2, player1.symbol());

        int row = gameBoard.placeDisc(3, player1.symbol());

        try {
            java.lang.reflect.Method checkWinMethod = GameLogic.class.getDeclaredMethod("checkWin", int.class, int.class);
            checkWinMethod.setAccessible(true);
            boolean result = (boolean) checkWinMethod.invoke(gameLogic, row, 3);

            assertTrue(result, "Horizontal win condition should be detected");
        } catch (Exception e) {
            fail("Exception occurred while testing horizontal win: " + e.getMessage());
        }
    }

    @Test
    void testVerticalWinCondition() {
        gameLogic = new GameLogic(gameBoard, player1, player2);

        gameBoard.placeDisc(3, player1.symbol());
        gameBoard.placeDisc(3, player1.symbol());
        gameBoard.placeDisc(3, player1.symbol());

        int row = gameBoard.placeDisc(3, player1.symbol());

        try {
            java.lang.reflect.Method checkWinMethod = GameLogic.class.getDeclaredMethod("checkWin", int.class, int.class);
            checkWinMethod.setAccessible(true);
            boolean result = (boolean) checkWinMethod.invoke(gameLogic, row, 3);

            assertTrue(result, "Vertical win condition should be detected");
        } catch (Exception e) {
            fail("Exception occurred while testing vertical win: " + e.getMessage());
        }
    }

    @Test
    void testDiagonalWinCondition1() {
        gameLogic = new GameLogic(gameBoard, player1, player2);

        // Create a diagonal win scenario (bottom-left to top-right)
        // X . . .
        // O X . .
        // O O X .
        // X O O X

        gameBoard.placeDisc(0, player1.symbol());
        gameBoard.placeDisc(1, player2.symbol());
        gameBoard.placeDisc(2, player2.symbol());
        gameBoard.placeDisc(3, player1.symbol());

        gameBoard.placeDisc(0, player2.symbol());
        gameBoard.placeDisc(1, player2.symbol());
        gameBoard.placeDisc(2, player1.symbol());

        gameBoard.placeDisc(0, player2.symbol());
        gameBoard.placeDisc(1, player1.symbol());

        int row = gameBoard.placeDisc(0, player1.symbol());

        try {
            java.lang.reflect.Method checkWinMethod = GameLogic.class.getDeclaredMethod("checkWin", int.class, int.class);
            checkWinMethod.setAccessible(true);
            boolean result = (boolean) checkWinMethod.invoke(gameLogic, row, 0);

            assertTrue(result, "Diagonal win condition (bottom-left to top-right) should be detected");
        } catch (Exception e) {
            fail("Exception occurred while testing diagonal win: " + e.getMessage());
        }
    }

    @Test
    void testDiagonalWinCondition2() {
        gameLogic = new GameLogic(gameBoard, player1, player2);

        // Create a diagonal win scenario (bottom-right to top-left)
        // . . . X
        // . . X O
        // . X O O
        // X O O O

        gameBoard.placeDisc(0, player1.symbol());
        gameBoard.placeDisc(1, player2.symbol());
        gameBoard.placeDisc(2, player2.symbol());
        gameBoard.placeDisc(3, player2.symbol());

        gameBoard.placeDisc(1, player1.symbol());
        gameBoard.placeDisc(2, player2.symbol());
        gameBoard.placeDisc(3, player2.symbol());

        gameBoard.placeDisc(2, player1.symbol());
        gameBoard.placeDisc(3, player2.symbol());

        int row = gameBoard.placeDisc(3, player1.symbol());

        try {
            java.lang.reflect.Method checkWinMethod = GameLogic.class.getDeclaredMethod("checkWin", int.class, int.class);
            checkWinMethod.setAccessible(true);
            boolean result = (boolean) checkWinMethod.invoke(gameLogic, row, 3);

            assertTrue(result, "Diagonal win condition (bottom-right to top-left) should be detected");
        } catch (Exception e) {
            fail("Exception occurred while testing diagonal win: " + e.getMessage());
        }
    }

    @Test
    void testDrawCondition() {
        // Create a new game logic instance for this test
        gameLogic = new GameLogic(gameBoard, player1, player2);

        char[] symbols = {player1.symbol(), player2.symbol()};

        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            for (int row = 0; row < GameBoard.ROWS; row++) {
                // Alternate symbols in a way that avoids wins
                char symbol = symbols[(col + row) % 2];
                gameBoard.placeDisc(col, symbol);
            }
        }

        assertTrue(gameBoard.isFull(), "Board should be full");

        // No player should have won
        try {
            java.lang.reflect.Method checkWinMethod = GameLogic.class.getDeclaredMethod("checkWin", int.class, int.class);
            checkWinMethod.setAccessible(true);
            boolean result = (boolean) checkWinMethod.invoke(gameLogic, GameBoard.ROWS - 1, GameBoard.COLUMNS - 1);

            assertFalse(result, "No win condition should be detected in a draw scenario");
        } catch (Exception e) {
            fail("Exception occurred while testing draw condition: " + e.getMessage());
        }
    }

    @Test
    void testInputValidation() {
        // Simulate user input: first invalid (out of range), then valid
        String simulatedInput = "8\n3\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        gameLogic = new GameLogic(gameBoard, player1, player2);

        try {
            java.lang.reflect.Method getPlayerMoveMethod = GameLogic.class.getDeclaredMethod("getPlayerMove");
            getPlayerMoveMethod.setAccessible(true);
            int column = (int) getPlayerMoveMethod.invoke(gameLogic);

            assertEquals(2, column, "getPlayerMove should return the valid column index");

            String output = outputStream.toString();
            assertTrue(output.contains("Invalid move") || output.contains("invalid input"), 
                    "Error message should be displayed for invalid input");

        } catch (Exception e) {
            fail("Exception occurred while testing input validation: " + e.getMessage());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    void testGameFlowWithWin() {
        // Simulate a game where player 1 wins horizontally
        // Player 1 places in columns 0,1,2,3 and player 2 places in columns 0,1,2
        // Include "n" at the end to respond "no" to the play again prompt
        String simulatedInput = "1\n1\n2\n2\n3\n3\n4\nn\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        gameLogic = new GameLogic(gameBoard, player1, player2);

        gameLogic.startGame();

        String output = outputStream.toString();
        assertTrue(output.contains("Player 1 wins"), 
                "Game should end with Player 1 winning");

        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testGameFlowWithDraw() {
        for (int col = 0; col < GameBoard.COLUMNS; col++) {
            for (int row = 0; row < GameBoard.ROWS; row++) {
                char symbol = (col + row) % 2 == 0 ? player1.symbol() : player2.symbol();
                gameBoard.placeDisc(col, symbol);
            }
        }

        gameLogic = new GameLogic(gameBoard, player1, player2);

        try {
            assertTrue(gameBoard.isFull(), "Board should be full");

            java.lang.reflect.Method checkWinMethod = GameLogic.class.getDeclaredMethod("checkWin", int.class, int.class);
            checkWinMethod.setAccessible(true);
            boolean winResult = (boolean) checkWinMethod.invoke(gameLogic, GameBoard.ROWS - 1, GameBoard.COLUMNS - 1);

            assertFalse(winResult, "No win should be detected in a draw scenario");

        } catch (Exception e) {
            fail("Exception occurred while testing game flow with draw: " + e.getMessage());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testSwitchPlayer() {
        gameLogic = new GameLogic(gameBoard, player1, player2);

        try {
            java.lang.reflect.Field currentPlayerField = GameLogic.class.getDeclaredField("currentPlayer");
            currentPlayerField.setAccessible(true);
            Player currentPlayer = (Player) currentPlayerField.get(gameLogic);

            assertEquals(player1, currentPlayer, "Initial current player should be player1");

            java.lang.reflect.Method switchPlayerMethod = GameLogic.class.getDeclaredMethod("switchPlayer");
            switchPlayerMethod.setAccessible(true);
            switchPlayerMethod.invoke(gameLogic);

            currentPlayer = (Player) currentPlayerField.get(gameLogic);
            assertEquals(player2, currentPlayer, "After switch, current player should be player2");

            switchPlayerMethod.invoke(gameLogic);

            currentPlayer = (Player) currentPlayerField.get(gameLogic);
            assertEquals(player1, currentPlayer, "After second switch, current player should be player1 again");

        } catch (Exception e) {
            fail("Exception occurred while testing switch player: " + e.getMessage());
        }
    }
}
