package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.exceptions.InvalidConfigException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ConfigTestParameterized {
    private static ConfigTestParameterModel[] testCases = {
            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c", 3, 'x', 'o', 'c'),

            new ConfigTestParameterModel("BOARD_LENGTH=10\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c", 10, 'x', 'o', 'c'),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=X\n" +
                    "COMPUTER_SYMBOL=c", 3, 'x', 'X', 'c'),

            new ConfigTestParameterModel("BOARD_LENGTH=5\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=X\n" +
                    "COMPUTER_SYMBOL=c", 5, 'x', 'X', 'c'),

            new ConfigTestParameterModel("BOARD_LENGTH= 7  \n\n" +
                    "PLAYER1_SYMBOL =  x\n" +
                    "PLAYER2_SYMBOL=o \n\n\n" +
                    "COMPUTER_SYMBOL= c", 7, 'x', 'o', 'c'),
    };

    @Parameterized.Parameter
    public ConfigTestParameterModel model;

    @Parameterized.Parameters
    public static Collection<ConfigTestParameterModel> data() {
        return Arrays.asList(testCases);
    }

    @Test
    public void testConfig() throws InvalidConfigException {
        Config config = new Config(model.reader);
        assertEquals(model.boardLength, config.getBoardLength());
        assertEquals(model.player1Symbol, config.getPlayer1Symbol());
        assertEquals(model.player2Symbol, config.getPlayer2Symbol());
        assertEquals(model.computerSymbol, config.getComputerSymbol());
    }
}