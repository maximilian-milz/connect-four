package com.example.connectfour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class GameStatsTest {

    private GameStats gameStats;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        gameStats = new GameStats();

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testConstructor() {
        gameStats.printStats();
        String output = outputStream.toString();

        assertTrue(output.contains("Total games played: 0"));
        assertTrue(output.contains("Draws: 0"));
        assertTrue(output.contains("No wins recorded yet."));
    }

    @Test
    void testRecordWin() {
        gameStats.recordWin("Player 1");

        gameStats.printStats();
        String output = outputStream.toString();

        assertTrue(output.contains("Total games played: 1"));
        assertTrue(output.contains("Player 1: 1"));

        gameStats.recordWin("Player 1");

        outputStream.reset();

        gameStats.printStats();
        output = outputStream.toString();

        assertTrue(output.contains("Total games played: 2"));
        assertTrue(output.contains("Player 1: 2"));

        gameStats.recordWin("Player 2");

        outputStream.reset();

        gameStats.printStats();
        output = outputStream.toString();

        assertTrue(output.contains("Total games played: 3"));
        assertTrue(output.contains("Player 1: 2"));
        assertTrue(output.contains("Player 2: 1"));
    }

    @Test
    void testRecordDraw() {
        gameStats.recordDraw();

        gameStats.printStats();
        String output = outputStream.toString();

        assertTrue(output.contains("Total games played: 1"));
        assertTrue(output.contains("Draws: 1"));

        gameStats.recordDraw();

        outputStream.reset();

        gameStats.printStats();
        output = outputStream.toString();

        assertTrue(output.contains("Total games played: 2"));
        assertTrue(output.contains("Draws: 2"));
    }

    @Test
    void testMixedWinsAndDraws() {
        gameStats.recordWin("Player 1");

        gameStats.recordDraw();

        gameStats.recordWin("Player 2");

        gameStats.printStats();
        String output = outputStream.toString();

        assertTrue(output.contains("Total games played: 3"));
        assertTrue(output.contains("Draws: 1"));
        assertTrue(output.contains("Player 1: 1"));
        assertTrue(output.contains("Player 2: 1"));
    }

    @Test
    void testPrintStatsFormat() {
        gameStats.recordWin("Player 1");
        gameStats.recordDraw();

        gameStats.printStats();
        String output = outputStream.toString();

        assertTrue(output.contains("===== Game Statistics ====="));
        assertTrue(output.contains("Total games played:"));
        assertTrue(output.contains("Draws:"));
        assertTrue(output.contains("Player wins:"));
        assertTrue(output.contains("==========================="));
    }
}
