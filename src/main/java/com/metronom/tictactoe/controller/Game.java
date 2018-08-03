package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.Board;
import com.metronom.tictactoe.entity.Coordinate;
import com.metronom.tictactoe.entity.Player;
import com.metronom.tictactoe.entity.PlayerBuilder;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

import java.util.Optional;

public class Game {
    private static final int MAX_WIN_SCORE = 5;

    private int turn;
    private int winScore;

    private Config config;
    private Board board = Board.getInstance();
    private Player[] players;
    private Player winner;
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

    public Config getConfig() {
        return config;
    }

    public Board getBoard() {
        return board;
    }

    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }

    public int getWinScore() {
        return winScore;
    }

    public void performAction(final Coordinate coordinate) throws InvalidCoordinateException {
        Player player = players[turn];
        board.put(player.getSymbol(), coordinate);

        turn++;

        if (turn == players.length)
            turn = 0;

        if (board.calculateHorizontalScore(coordinate) >= winScore
                || board.calculateVerticalScore(coordinate) >= winScore
                || board.calculateDiagonal1Score(coordinate) >= winScore
                || board.calculateDiagonal2Score(coordinate) >= winScore) {
            status = GameStatus.GAME_OVER;
            winner = player;
        } else if (board.getFreeRoomCount() == 0) {
            status = GameStatus.GAME_OVER;
        }
    }

    public void start() {
        status = GameStatus.RUNNING;
    }
}
