package com.example.connectfour;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks game statistics for Connect Four.
 * This includes wins for each player and the number of draws.
 */
public class GameStats {
    private final Map<String, Integer> playerWins;
    private int draws;
    private int gamesPlayed;
    
    /**
     * Creates a new game statistics tracker.
     */
    public GameStats() {
        playerWins = new HashMap<>();
        draws = 0;
        gamesPlayed = 0;
    }
    
    /**
     * Records a win for the specified player.
     * 
     * @param playerName The name of the player who won
     */
    public void recordWin(String playerName) {
        playerWins.put(playerName, playerWins.getOrDefault(playerName, 0) + 1);
        gamesPlayed++;
    }
    
    /**
     * Records a draw.
     */
    public void recordDraw() {
        draws++;
        gamesPlayed++;
    }

    /**
     * Prints the current game statistics to the console.
     */
    public void printStats() {
        System.out.println("\n===== Game Statistics =====");
        System.out.println("Total games played: " + gamesPlayed);
        System.out.println("Draws: " + draws);
        
        System.out.println("\nPlayer wins:");
        if (playerWins.isEmpty()) {
            System.out.println("No wins recorded yet.");
        } else {
            for (Map.Entry<String, Integer> entry : playerWins.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        System.out.println("===========================\n");
    }
}