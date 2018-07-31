package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.*;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

public class Game {
    private static final int MAX_WIN_SCORE = 5;

    private int turn;
    private int winScore;

    private Config config;
    private Board board = Board.getInstance();
    private Player[] players;
    private GameStatus status;

    public Game(final Config config) {
        this.config = config;
        this.winScore = Math.min(config.getBoardLength(), MAX_WIN_SCORE);

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
        return board.getCopyOfTable();
    }

    public Config getConfig() {
        return config;
    }

    public void performAction(final Coordinate coordinate) throws InvalidCoordinateException {
        Player player = players[turn];
        CellScore score = board.put(player, coordinate);

        turn++;

        if (turn == players.length)
            turn = 0;

        if (score.isGreaterThan(winScore)) {
            if (player.equals(players[0]))
                status = GameStatus.PLAYER1_IS_WINNER;
            else if (player.equals(players[1]))
                status = GameStatus.PLAYER2_IS_WINNER;
            else status = GameStatus.AI_IS_WINNER;
        } else if (board.getFreeRoomCount() == 0)
            status = GameStatus.GAME_OVER;
    }

    public void start() {
        status = GameStatus.RUNNING;
    }
}
