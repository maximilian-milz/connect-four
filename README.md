# Connect Four Console Game

A clean and robust console-based implementation of the classic Connect Four game in Java.

## Overview

This project implements a two-player Connect Four game that runs in the console. Players take turns dropping colored discs into a 7x6 grid, with the goal of connecting four of their discs vertically, horizontally, or diagonally.

## Features

- 7x6 game board
- Two-player gameplay with customizable player names
- Input validation and error handling
- Win detection for horizontal, vertical and diagonal connections
- Draw detection when the board is full
- Game statistics tracking (wins per player, draws, total games)
- Option to play again after a game ends

## Requirements

- Java 17 or higher
- Gradle (for building and running tests)

## How to Run

1. Clone the repository
2. Navigate to the project directory
3. Build the project with Gradle:
   ```
   ./gradlew build
   ```
4. Run the game:
   ```
   java -cp build/classes/java/main com.example.connectfour.ConnectFour
   ```

## How to Play

1. When prompted, enter names for Player 1 and Player 2 (or press Enter to use default names)
2. On your turn, enter a column number (1-7) to drop your disc
3. The first player to connect four discs in a row (horizontally, vertically, or diagonally) wins
4. If the board fills up without a winner, the game ends in a draw
5. After a game ends, you can choose to play again or exit

## Project Structure

- `ConnectFour`: Main class to start and control the game
- `GameBoard`: Manages the board state and rendering
- `Player`: Represents a player with a name and symbol
- `GameLogic`: Contains the game loop, move validation, and win/draw checks
- `GameStats`: Tracks game statistics

## Testing

The project includes comprehensive unit tests for all components. Run the tests with:

```
./gradlew test
```