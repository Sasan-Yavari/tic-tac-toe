package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.Coordinate;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class GameTestParameterized {
    private Game game;

    @Parameterized.Parameter
    public Coordinate[] coordinates;

    @Parameterized.Parameters
    public static Collection<Coordinate[][]> data() {
        Coordinate[][][] winningScenarios = {
                // winner is x with a vertical line in column 0
                {{
                        new Coordinate(0, 0), //x
                        new Coordinate(0, 1), //o
                        new Coordinate(0, 2), //c
                        new Coordinate(1, 0), //x
                        new Coordinate(1, 1), //o
                        new Coordinate(1, 2), //c
                        new Coordinate(2, 0), //x
                }},

                // winner is x with a horizontal line in row 0
                {{
                        new Coordinate(0, 0), //x
                        new Coordinate(1, 0), //o
                        new Coordinate(2, 0), //c
                        new Coordinate(0, 1), //x
                        new Coordinate(1, 1), //o
                        new Coordinate(2, 1), //c
                        new Coordinate(0, 2), //x
                }},

                // winner is x with a line in one of diagonals
                {{
                        new Coordinate(0, 0), //x
                        new Coordinate(0, 1), //o
                        new Coordinate(0, 2), //c
                        new Coordinate(1, 1), //x
                        new Coordinate(1, 0), //o
                        new Coordinate(1, 2), //c
                        new Coordinate(2, 2), //x
                }},

                // winner is x with a line in other diagonal
                {{
                        new Coordinate(0, 2), //x
                        new Coordinate(0, 0), //o
                        new Coordinate(0, 1), //c
                        new Coordinate(1, 1), //x
                        new Coordinate(1, 0), //o
                        new Coordinate(1, 2), //c
                        new Coordinate(2, 0), //x
                }},
        };

        return Arrays.asList(winningScenarios);
    }

    @Before
    public void setUp() throws Exception {
        StringReader reader = new StringReader(
                "BOARD_LENGTH=3\n" +
                        "PLAYER1_SYMBOL=x\n" +
                        "PLAYER2_SYMBOL=o\n" +
                        "COMPUTER_SYMBOL=c");
        Config config = new Config(reader);
        game = new Game(config);
    }

    @Test
    public void performAction() throws InvalidCoordinateException {
        for (Coordinate coordinate : coordinates)
            game.performAction(coordinate);

        assertEquals(GameStatus.GAME_OVER, game.getStatus());
        assertTrue(game.getWinner().isPresent());
    }
}