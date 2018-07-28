package com.metronom.tictactoe.conf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ConfigTest {
    private static ConfigTestParameterModel[] testCases = {
            // Exceptional test cases
            new ConfigTestParameterModel("Config input stream is null"),

            new ConfigTestParameterModel("",
                    "BOARD_LENGTH must be between 3 and 10"),

            new ConfigTestParameterModel("BOARD_LENGTH=1\n" +
                    "COMPUTER_SYMBOL=c",
                    "BOARD_LENGTH must be between 3 and 10"),

            new ConfigTestParameterModel("BOARD_LENGTH=1\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c",
                    "BOARD_LENGTH must be between 3 and 10"),

            new ConfigTestParameterModel("BOARD_LENGTH=11\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c",
                    "BOARD_LENGTH must be between 3 and 10"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c",
                    "PLAYER1_SYMBOL must be 1 character"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER2_SYMBOL=x\n" +
                    "COMPUTER_SYMBOL=c",
                    "PLAYER1_SYMBOL must be 1 character"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=x\n" +
                    "COMPUTER_SYMBOL=c",
                    "Players must have different symbols"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=X\n" +
                    "COMPUTER_SYMBOL=x",
                    "Players must have different symbols"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=oo\n" +
                    "COMPUTER_SYMBOL=c",
                    "PLAYER2_SYMBOL must be 1 character"),

            // Valid test cases
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
    public void testConfig() {
        try {
            Config config = new Config(model.reader);

            if (model.errorMessage != null)
                fail();

            assertEquals(model.boardLength, config.getBoardLength());
            assertEquals(model.player1Symbol, config.getPlayer1Symbol());
            assertEquals(model.player2Symbol, config.getPlayer2Symbol());
            assertEquals(model.computerSymbol, config.getComputerSymbol());
        } catch (InvalidConfigException | NullPointerException ex) {
            assertEquals(model.errorMessage, ex.getMessage());
        }
    }
}