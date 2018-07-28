package com.metronom.tictactoe.conf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ConfigTest {
    private static Object[][] testCases = {
            {null, "Config input stream is null", -1, '-', '-', '-'},

            {new StringBufferInputStream(""), "BOARD_LENGTH must be between 3 and 10", -1, '-', '-', '-'},

            {new StringBufferInputStream("BOARD_LENGTH=1\n" +
                    "COMPUTER_SYMBOL=c"), "BOARD_LENGTH must be between 3 and 10", -1, '-', '-', '-'},

            {new StringBufferInputStream("BOARD_LENGTH=1\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"), "BOARD_LENGTH must be between 3 and 10", -1, '-', '-', '-'},

            {new StringBufferInputStream("BOARD_LENGTH=11\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"), "BOARD_LENGTH must be between 3 and 10", -1, '-', '-', '-'},

            {new StringBufferInputStream("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"), null, 3, 'x', 'o', 'c'},

            {new StringBufferInputStream("BOARD_LENGTH=10\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=o\n" +
                    "COMPUTER_SYMBOL=c"), null, 10, 'x', 'o', 'c'},

            {new StringBufferInputStream("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=x\n" +
                    "COMPUTER_SYMBOL=c"), "Players must have different symbols", -1, '-', '-', '-'},

            {new StringBufferInputStream("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=X\n" +
                    "COMPUTER_SYMBOL=c"), null, 3, 'x', 'X', 'c'},

            {new StringBufferInputStream("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=X\n" +
                    "COMPUTER_SYMBOL=x"), "Players must have different symbols", -1, '-', '-', '-'},

            {new StringBufferInputStream("BOARD_LENGTH=3\n" +
                    "PLAYER1_SYMBOL=x\n" +
                    "PLAYER2_SYMBOL=oo\n" +
                    "COMPUTER_SYMBOL=c"), "PLAYER2_SYMBOL must be 1 character", -1, '-', '-', '-'},
    };

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(testCases);
    }

    @Parameterized.Parameter
    public InputStream input;

    @Parameterized.Parameter(1)
    public String errorMessage;

    @Parameterized.Parameter(2)
    public int boardLength;

    @Parameterized.Parameter(3)
    public char player1Symbol;

    @Parameterized.Parameter(4)
    public char player2Symbol;

    @Parameterized.Parameter(5)
    public char computerSymbol;

    @Test
    public void testConfig() {
        try {
            Config config = new Config(input);

            if (errorMessage != null)
                fail();

            assertEquals(boardLength, config.getBoardLength());
            assertEquals(player1Symbol, config.getPlayer1Symbol());
            assertEquals(player2Symbol, config.getPlayer2Symbol());
            assertEquals(computerSymbol, config.getComputerSymbol());
        } catch (IOException | InvalidConfigException | NullPointerException ex) {
            assertEquals(errorMessage, ex.getMessage());
        }
    }
}