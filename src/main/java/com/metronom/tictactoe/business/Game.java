package com.metronom.tictactoe.business;

import com.metronom.tictactoe.business.entity.*;
import com.metronom.tictactoe.business.enums.GameStatus;
import com.metronom.tictactoe.business.exceptions.InvalidCoordinateException;
import com.metronom.tictactoe.business.entity.Config;
import com.metronom.tictactoe.ui.IUserInterface;

public class Game {
    private static final int MAX_WIN_LENGTH = 5;

    private static Game instance = new Game();

    private int turn;
    private int winLength;

    private Config config;
    private Board board = Board.getInstance();
    private IUserInterface ui;
    private AbstractPlayer[] players;
    private GameStatus status;

    public static Game getInstance() {
        return instance;
    }

    private Game() {
    }

    public void init(Config config, IUserInterface ui) {
        this.config = config;

        board.init(config.getBoardLength());

        this.ui = ui;
        this.winLength = Math.min(config.getBoardLength(), MAX_WIN_LENGTH);
        this.players = new AbstractPlayer[3];
        this.players[0] = new HumanPlayer("Player1", config.getPlayer1Symbol());
        this.players[1] = new HumanPlayer("Player2", config.getPlayer2Symbol());
        this.players[2] = new AIPlayer("AI", config.getComputerSymbol());

        this.turn = 0;
        this.status = GameStatus.NOT_STARTED;
    }

    public void play(Point point) throws InvalidCoordinateException {
        board.put(point, players[turn]);

        turn++;

        if (turn == players.length)
            turn = 0;

        updateStatus();
        ui.update();
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

    // TODO implement
    private void updateStatus() {
        if (board.getFreeRoomCount() == 0)
            status = GameStatus.GAME_OVER;
    }
}
