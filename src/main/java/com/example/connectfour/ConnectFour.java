package com.example.connectfour;

import java.util.Scanner;

/**
 * Main entry point for the Connect Four game.
 * This class initializes the game components and starts the game.
 */
public class ConnectFour {

    /**
     * Main method to start the Connect Four game.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Connect Four!");

        // Get player names
        Scanner scanner = new Scanner(System.in);
        String player1Name = getPlayerName(scanner, 1);
        String player2Name = getPlayerName(scanner, 2);

        // Initialize game components
        GameBoard gameBoard = new GameBoard();
        Player player1 = new Player(player1Name, 'X');
        Player player2 = new Player(player2Name, 'O');
        GameLogic gameLogic = new GameLogic(gameBoard, player1, player2);

        // Start the game
        gameLogic.startGame();

        // Close the scanner (although GameLogic will also close it)
        scanner.close();
    }

    /**
     * Prompts the user to enter a name for the specified player.
     * 
     * @param scanner The scanner to read input from
     * @param playerNumber The player number (1 or 2)
     * @return The player name entered by the user
     */
    private static String getPlayerName(Scanner scanner, int playerNumber) {
        System.out.println("Enter name for Player " + playerNumber + " (default: Player " + playerNumber + "):");
        String name = scanner.nextLine().trim();

        // Use default name if empty input
        if (name.isEmpty()) {
            name = "Player " + playerNumber;
            System.out.println("Using default name: " + name);
        }

        return name;
    }
}
