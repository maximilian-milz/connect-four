package com.example.connectfour;

/**
 * Represents a player in the Connect Four game.
 * Each player has a name and a symbol (disc character).
 */
public class Player {
    private final String name;
    private final char symbol;
    
    /**
     * Creates a new player with the specified name and symbol.
     * 
     * @param name The player's name
     * @param symbol The character symbol representing the player's disc
     */
    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }
    
    /**
     * Gets the player's name.
     * 
     * @return The player's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the player's symbol.
     * 
     * @return The character symbol representing the player's disc
     */
    public char getSymbol() {
        return symbol;
    }
}