package com.metronom.tictactoe.controller.enums;

public enum GameStatus {
    RUNNING("Running"),
    GAME_OVER("Game Over");

    private String message;

    GameStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
