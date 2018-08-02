package com.metronom.tictactoe.controller.enums;

public enum GameStatus {
    NOT_STARTED("Not started"),
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
