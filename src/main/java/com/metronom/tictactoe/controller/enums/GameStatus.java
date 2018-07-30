package com.metronom.tictactoe.controller.enums;

public enum GameStatus {
    NOT_STARTED("Not started"),
    RUNNING("Running"),
    GAME_OVER("Game Over"),
    PLAYER1_IS_WINNER("Winner is Player 1"),
    PLAYER2_IS_WINNER("Winner is Player 2"),
    AI_IS_WINNER("Winner is Computer");

    private String message;

    GameStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
