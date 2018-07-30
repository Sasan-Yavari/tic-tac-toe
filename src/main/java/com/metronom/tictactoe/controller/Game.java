package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.entity.AbstractPlayer;
import com.metronom.tictactoe.entity.Board;
import com.metronom.tictactoe.entity.PlayerFactory;
import com.metronom.tictactoe.entity.Point;
import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

public class Game {
    private static final int MAX_WIN_LENGTH = 5;

    private static Game instance = new Game();

    private int turn;
    private int winLength;

    private Config config;
    private Board board = Board.getInstance();
    private AbstractPlayer[] players;
    private GameStatus status;

    public static Game getInstance() {
        return instance;
    }

    private Game() {
    }

    public void init(Config config) {
        this.config = config;

        board.init(config.getBoardLength());

        this.winLength = Math.min(config.getBoardLength(), MAX_WIN_LENGTH);
        this.players = new AbstractPlayer[3];
        this.players[0] = PlayerFactory.createPlayer("Player1", config.getPlayer1Symbol(), false);
        this.players[1] = PlayerFactory.createPlayer("Player2", config.getPlayer2Symbol(), false);
        this.players[2] = PlayerFactory.createPlayer("AI", config.getComputerSymbol(), true);

        this.turn = 0;
        this.status = GameStatus.NOT_STARTED;
    }

    public void actionPerformed(Point point) throws InvalidCoordinateException {
        AbstractPlayer player = players[turn];

        board.put(point, player);

        turn++;

        if (turn == players.length)
            turn = 0;

        updateStatus();
    }

    public GameStatus getStatus() {
        return status;
    }

    public void start() {
        status = GameStatus.RUNNING;
    }

    public AbstractPlayer getNextPlayer() {
        return players[turn];
    }

    public AbstractPlayer[][] getCopyOfBoardMatrix() {
        return board.getCopyOfBoardMatrix();
    }

    public Config getConfig() {
        return config;
    }

    private void updateStatus() {
        // TODO implement
        if (board.getFreeRoomCount() == 0)
            status = GameStatus.GAME_OVER;
    }
}
