package com.example.connectfour;

/**
 * Represents a player in the Connect Four game.
 * Each player has a name and a symbol (disc character).
 */
public record Player(String name, char symbol) {}