package com.example.connectfour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests for the GameStats class.
 */
public class GameStatsTest {
    
    private GameStats gameStats;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;
    
    @BeforeEach
    void setUp() {
        gameStats = new GameStats();
        
        // Redirect System.out to capture output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    
    /**
     * Test that the GameStats constructor initializes properly.
     */
    @Test
    void testConstructor() {
        // A new GameStats should have 0 games played
        gameStats.printStats();
        String output = outputStream.toString();
        
        assertTrue(output.contains("Total games played: 0"));
        assertTrue(output.contains("Draws: 0"));
        assertTrue(output.contains("No wins recorded yet."));
    }
    
    /**
     * Test recording a win for a player.
     */
    @Test
    void testRecordWin() {
        // Record a win for Player 1
        gameStats.recordWin("Player 1");
        
        // Print stats and check output
        gameStats.printStats();
        String output = outputStream.toString();
        
        assertTrue(output.contains("Total games played: 1"));
        assertTrue(output.contains("Player 1: 1"));
        
        // Record another win for Player 1
        gameStats.recordWin("Player 1");
        
        // Reset output stream
        outputStream.reset();
        
        // Print stats and check output again
        gameStats.printStats();
        output = outputStream.toString();
        
        assertTrue(output.contains("Total games played: 2"));
        assertTrue(output.contains("Player 1: 2"));
        
        // Record a win for Player 2
        gameStats.recordWin("Player 2");
        
        // Reset output stream
        outputStream.reset();
        
        // Print stats and check output again
        gameStats.printStats();
        output = outputStream.toString();
        
        assertTrue(output.contains("Total games played: 3"));
        assertTrue(output.contains("Player 1: 2"));
        assertTrue(output.contains("Player 2: 1"));
    }
    
    /**
     * Test recording a draw.
     */
    @Test
    void testRecordDraw() {
        // Record a draw
        gameStats.recordDraw();
        
        // Print stats and check output
        gameStats.printStats();
        String output = outputStream.toString();
        
        assertTrue(output.contains("Total games played: 1"));
        assertTrue(output.contains("Draws: 1"));
        
        // Record another draw
        gameStats.recordDraw();
        
        // Reset output stream
        outputStream.reset();
        
        // Print stats and check output again
        gameStats.printStats();
        output = outputStream.toString();
        
        assertTrue(output.contains("Total games played: 2"));
        assertTrue(output.contains("Draws: 2"));
    }
    
    /**
     * Test a mix of wins and draws.
     */
    @Test
    void testMixedWinsAndDraws() {
        // Record a win for Player 1
        gameStats.recordWin("Player 1");
        
        // Record a draw
        gameStats.recordDraw();
        
        // Record a win for Player 2
        gameStats.recordWin("Player 2");
        
        // Print stats and check output
        gameStats.printStats();
        String output = outputStream.toString();
        
        assertTrue(output.contains("Total games played: 3"));
        assertTrue(output.contains("Draws: 1"));
        assertTrue(output.contains("Player 1: 1"));
        assertTrue(output.contains("Player 2: 1"));
    }
    
    /**
     * Test the format of the printed statistics.
     */
    @Test
    void testPrintStatsFormat() {
        // Record some stats
        gameStats.recordWin("Player 1");
        gameStats.recordDraw();
        
        // Print stats and check output format
        gameStats.printStats();
        String output = outputStream.toString();
        
        assertTrue(output.contains("===== Game Statistics ====="));
        assertTrue(output.contains("Total games played:"));
        assertTrue(output.contains("Draws:"));
        assertTrue(output.contains("Player wins:"));
        assertTrue(output.contains("==========================="));
    }
    
    @Test
    void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }
}