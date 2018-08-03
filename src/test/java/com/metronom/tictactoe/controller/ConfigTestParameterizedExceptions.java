package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.exceptions.InvalidConfigException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConfigTestParameterizedExceptions {
    private static ConfigTestParameterModel[] testCases = {
            new ConfigTestParameterModel("Config input stream is null"),
            new ConfigTestParameterModel(""),
            new ConfigTestParameterModel("BOARD_LENGTH=1\nCOMPUTER_SYMBOL=c"),
            new ConfigTestParameterModel("BOARD_LENGTH=1\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"),

            new ConfigTestParameterModel("BOARD_LENGTH=11\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER2_SYMBOL=x\n" +
                    "COMPUTER_SYMBOL=c"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=x\n" +
                    "COMPUTER_SYMBOL=c"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=X\n" +
                    "COMPUTER_SYMBOL=x"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=oo\n" +
                    "COMPUTER_SYMBOL=c"),

            new ConfigTestParameterModel("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=\n" +
                    "COMPUTER_SYMBOL=c"),

            new ConfigTestParameterModel("BOARD_LENGTH=a\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"),
    };

    @Parameterized.Parameter
    public ConfigTestParameterModel model;

    @Parameterized.Parameters
    public static Collection<ConfigTestParameterModel> data() {
        return Arrays.asList(testCases);
    }

    @Test(expected = InvalidConfigException.class)
    public void testConfig() throws InvalidConfigException {
        new Config(model.reader);
    }
}