package com.metronom.tictactoe.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.*;

public class ConfigTest {
    private Config config;

    @Before
    public void setUp() throws Exception {
        StringReader reader = new StringReader(
                "BOARD_LENGTH=3\n" +
                        "PLAYER1_SYMBOL=x\n" +
                        "PLAYER2_SYMBOL=o\n" +
                        "COMPUTER_SYMBOL=c");
        config = new Config(reader);
    }

    @Test
    public void getBoardLength() {
        assertEquals(3, config.getBoardLength());
    }

    @Test
    public void getPlayer1Symbol() {
        assertEquals('x', config.getPlayer1Symbol());
    }

    @Test
    public void getPlayer2Symbol() {
        assertEquals('o', config.getPlayer2Symbol());
    }

    @Test
    public void getComputerSymbol() {
        assertEquals('c', config.getComputerSymbol());
    }
}