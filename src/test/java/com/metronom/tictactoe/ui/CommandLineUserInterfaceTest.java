package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import org.junit.Before;
import org.junit.Test;

import java.io.StringBufferInputStream;
import java.io.StringReader;

public class CommandLineUserInterfaceTest {
    private CommandLineUserInterface ui = CommandLineUserInterface.getInstance();
    private StringBufferInputStream inputStream = new StringBufferInputStream("3,1\n2,1\n3,2\n2,2\n3,3");

    @Before
    public void setUp() throws Exception {
        StringReader reader = new StringReader(
                "BOARD_LENGTH=3\n" +
                        "PLAYER1_SYMBOL=x\n" +
                        "PLAYER2_SYMBOL=o\n" +
                        "COMPUTER_SYMBOL=c");
        ui.show(new Game(new Config(reader)), inputStream);
    }

    @Test(timeout = 3000)
    public void startGame() {
        ui.startGame();
    }
}