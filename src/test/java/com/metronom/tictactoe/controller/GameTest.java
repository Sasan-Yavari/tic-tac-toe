package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.Coordinate;
import com.metronom.tictactoe.entity.Player;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Optional;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Config config;

    @Before
    public void setUp() throws Exception {
        StringReader reader = new StringReader(
                "BOARD_LENGTH=3\n" +
                        "PLAYER1_SYMBOL=x\n" +
                        "PLAYER2_SYMBOL=o\n" +
                        "COMPUTER_SYMBOL=c");
        config = new Config(reader);
        game = new Game(config);
    }

    @Test
    public void getStatus() {
        assertEquals(GameStatus.NOT_STARTED, game.getStatus());
        game.start();
        assertEquals(GameStatus.RUNNING, game.getStatus());
    }

    @Test
    public void getNextPlayer() throws InvalidCoordinateException {
        Player player1 = game.getNextPlayer();
        assertNotNull(player1);
        assertEquals(player1, game.getNextPlayer());
        assertEquals('x', player1.getSymbol());

        game.performAction(new Coordinate(0, 0)); //x
        Player player2 = game.getNextPlayer();
        assertNotEquals(player1, player2);
        assertEquals('o', player2.getSymbol());

        game.performAction(new Coordinate(0, 1)); //o
        Player player3 = game.getNextPlayer();
        assertNotEquals(player1, player3);
        assertEquals('c', player3.getSymbol());

        game.performAction(new Coordinate(0, 2)); //c
        Player player4 = game.getNextPlayer();
        assertEquals(player1, player4);
    }

    @Test
    public void getConfig() {
        assertEquals(config, game.getConfig());
    }

    @Test
    public void getBoard() {
        assertNotNull(game.getBoard());
    }

    @Test
    public void getWinner() {
        assertFalse(game.getWinner().isPresent());
    }

    @Test
    public void start() {
        game.start();
        assertEquals(GameStatus.RUNNING, game.getStatus());
    }

    @Test(expected = InvalidCoordinateException.class)
    public void performActionWrongCoordinate() throws InvalidCoordinateException {
        game.performAction(new Coordinate(-1, 20)); //x
    }

    @Test(expected = InvalidCoordinateException.class)
    public void performActionDuplicatePlay() throws InvalidCoordinateException {
        game.performAction(new Coordinate(0, 0)); //x
        game.performAction(new Coordinate(0, 0)); //o
    }

    @Test
    public void performActionFullGameWithWinner() throws InvalidCoordinateException {
        game.start();

        Player player1 = game.getNextPlayer();
        assertEquals('x', player1.getSymbol());
        game.performAction(new Coordinate(0, 0)); //x
        assertEquals(GameStatus.RUNNING, game.getStatus());
        assertFalse(game.getWinner().isPresent());

        Player player2 = game.getNextPlayer();
        assertEquals('o', player2.getSymbol());
        game.performAction(new Coordinate(1, 0)); //o
        assertEquals(GameStatus.RUNNING, game.getStatus());
        assertFalse(game.getWinner().isPresent());

        Player player3 = game.getNextPlayer();
        assertEquals('c', player3.getSymbol());
        game.performAction(new Coordinate(2, 0)); //c
        assertEquals(GameStatus.RUNNING, game.getStatus());
        assertFalse(game.getWinner().isPresent());

        assertNotEquals(player1, player2);
        assertNotEquals(player1, player3);
        assertNotEquals(player2, player3);

        game.performAction(new Coordinate(0, 1)); //x
        game.performAction(new Coordinate(1, 1)); //o
        game.performAction(new Coordinate(2, 1)); //c

        game.performAction(new Coordinate(0, 2)); //x

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertEquals(Optional.of(player1), game.getWinner());
    }

    @Test
    public void performActionFullGameWithoutWinner() throws InvalidCoordinateException {
        game.start();

        game.performAction(new Coordinate(0, 0)); //x
        game.performAction(new Coordinate(1, 0)); //o
        game.performAction(new Coordinate(2, 0)); //c

        game.performAction(new Coordinate(0, 1)); //x
        game.performAction(new Coordinate(1, 1)); //o
        game.performAction(new Coordinate(2, 1)); //c

        game.performAction(new Coordinate(1, 2)); //x
        game.performAction(new Coordinate(2, 2)); //o
        game.performAction(new Coordinate(0, 2)); //c

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertFalse(game.getWinner().isPresent());
    }

    @Test
    public void getWinScore() {
        assertTrue(game.getWinScore() <= config.getBoardLength());
    }
}