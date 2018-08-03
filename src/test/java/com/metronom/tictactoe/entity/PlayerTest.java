package com.metronom.tictactoe.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
    private Board board = Board.getInstance();
    private Player player1;
    private Player player1Copy;
    private Player ai;

    @Before
    public void setUp() throws Exception {
        board.init(3);

        PlayerBuilder builder = new PlayerBuilder();
        player1 = builder.withName("Player1").withSymbol('x').build();
        player1Copy = builder.withName("Player1Copy").withSymbol('x').build();
        ai = builder.withName("AI").withSymbol('c').withAISupport().build();
    }

    @Test
    public void getNextMove() {
        assertEquals(Optional.empty(), player1.getNextMove(board));
        assertTrue(ai.getNextMove(board).isPresent());
    }

    @Test
    public void getName() {
        assertEquals("Player1", player1.getName());
        assertEquals("AI", ai.getName());
    }

    @Test
    public void getSymbol() {
        assertEquals('x', player1.getSymbol());
        assertEquals('c', ai.getSymbol());
    }

    @Test
    public void equals() {
        assertEquals(player1, player1);
        assertEquals(player1, player1Copy);
        assertNotEquals(player1, ai);
    }
}