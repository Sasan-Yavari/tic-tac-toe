package com.metronom.tictactoe.conf;

import com.metronom.tictactoe.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private int boardLength;
    private char player1Symbol;
    private char player2Symbol;
    private char computerSymbol;

    public Config(InputStream input) throws IOException, InvalidConfigException {
        if (input == null)
            throw new NullPointerException("Config input stream is null");

        Properties props = new Properties();
        props.load(input);

        if (props.containsKey("BOARD_LENGTH"))
            boardLength = Util.tryToInt(props.getProperty("BOARD_LENGTH").trim(), -1);

        if (boardLength < 3 || boardLength > 10)
            throw new InvalidConfigException("BOARD_LENGTH must be between 3 and 10");

        String tmp = props.getProperty("PLAYER1_SYMBOL");
        if (tmp != null && tmp.trim().length() == 1)
            player1Symbol = tmp.trim().charAt(0);
        else throw new InvalidConfigException("PLAYER1_SYMBOL must be 1 character");

        tmp = props.getProperty("PLAYER2_SYMBOL");
        if (tmp != null && tmp.trim().length() == 1)
            player2Symbol = tmp.trim().charAt(0);
        else throw new InvalidConfigException("PLAYER2_SYMBOL must be 1 character");

        tmp = props.getProperty("COMPUTER_SYMBOL");
        if (tmp != null && tmp.trim().length() == 1)
            computerSymbol = tmp.trim().charAt(0);
        else throw new InvalidConfigException("COMPUTER_SYMBOL must be 1 character");

        if (player1Symbol == player2Symbol || player1Symbol == computerSymbol || player2Symbol == computerSymbol)
            throw new InvalidConfigException("Players must have different symbols");
    }

    public int getBoardLength() {
        return boardLength;
    }

    public char getPlayer1Symbol() {
        return player1Symbol;
    }

    public char getPlayer2Symbol() {
        return player2Symbol;
    }

    public char getComputerSymbol() {
        return computerSymbol;
    }
}
