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

    /**
     * Instantiates and initializes the game. This is the main game controller.
     *
     * @param config instance of {@link Config}
     */
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
        this.status = GameStatus.RUNNING;
    }

    /**\
     * Gives the status of the game. The status type is {@link GameStatus}.
     *
     * @return {@link GameStatus} that indicates the final status of the game at the time.
     * This can be {@code GameStatus.RUNNING} or {@code GameStatus.GAME_OVER}
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Gives the player that will play next. By calling this method we can know that who's turn to play.
     *
     * @return instance of {@link Player}
     */
    public Player getNextPlayer() {
        return players[turn];
    }

    /**
     * Gives the config of the running istance of the game.
     *
     * @return instance of {@link Config}
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Gives the game board.
     *
     * @return instance of {@link Board}
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gives the winner of the game if there is a winner or {@code Optional.empty()}
     *
     * @return {@code Optional<Player>}
     */
    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }

    /**
     * Gives the score a player needs for winning.
     * This is the value of {@code Math.min(config.getBoardLength(), MAX_WIN_SCORE)}
     * <br/><br/>
     * A player is the winner if he/she/it has a line on the board with the {@code length = winScore}
     *
     * @return The score a player needs for winning.
     */
    public int getWinScore() {
        return winScore;
    }

    /**
     * This is the method that controls the game.<br/>
     * Calling this method will put the next players' character on the coordinate location of the board.<br/>
     * After putting the character, the score of the player will be calculated and game status will be updated.
     *
     * @param coordinate the location of the next character
     * @throws InvalidCoordinateException if the coordinate is not inside the board or if the located cell is
     * already full.
     */
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
}
