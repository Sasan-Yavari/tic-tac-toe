package com.metronom.tictactoe.business;

public enum PlayResult {
    DONE,
    GAME_OVER("Game Over"),
    PLAYER1_WON("Winner is Player 1"),
    PLAYER2_WON("Winner is Player 2"),
    AI_WON("Winner is Computer");

    private String message;

    PlayResult() {}

    PlayResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
