package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.Board;
import com.metronom.tictactoe.entity.Player;
import com.metronom.tictactoe.entity.PlayerBuilder;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

import java.awt.*;

public class Game {
    private static final int MAX_WIN_LENGTH = 5;

    private int turn;
    private int winLength;

    private Config config;
    private Board board = Board.getInstance();
    private Player[] players;
    private GameStatus status;

    public Game(final Config config) {
        this.config = config;
        this.winLength = Math.min(config.getBoardLength(), MAX_WIN_LENGTH);

        board.init(config.getBoardLength());

        PlayerBuilder builder = new PlayerBuilder();

        this.players = new Player[3];
        this.players[0] = builder.withName("Player1").withSymbol(config.getPlayer1Symbol()).build();
        this.players[1] = builder.withName("Player2").withSymbol(config.getPlayer2Symbol()).build();
        this.players[2] = builder.withName("AI").withSymbol(config.getComputerSymbol()).withAISupport().build();

        this.turn = 0;
        this.status = GameStatus.NOT_STARTED;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getNextPlayer() {
        return players[turn];
    }

    public Player[][] getCopyOfBoardMatrix() {
        return board.getCopyOfBoardMatrix();
    }

    public Config getConfig() {
        return config;
    }

    public void performAction(Point point) throws InvalidCoordinateException {
        Player player = players[turn];

        board.put(point, player);

        turn++;

        if (turn == players.length)
            turn = 0;

        updateStatus();
    }

    public void start() {
        status = GameStatus.RUNNING;
    }

    private void updateStatus() {
        // TODO implement
        if (board.getFreeRoomCount() == 0)
            status = GameStatus.GAME_OVER;
    }
}
