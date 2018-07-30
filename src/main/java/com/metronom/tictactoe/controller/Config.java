package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.ConfigKey;
import com.metronom.tictactoe.exceptions.InvalidConfigException;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class Config {
    private static final String MESSAGE_INPUT_STREAM_IS_NULL = "Config input stream is null";
    private static final String MESSAGE_LOAD_ERROR = "Can not load configs from input";
    private static final String MESSAGE_INVALID_BOARD_LENGTH = "Board length must be between 3 and 10";
    private static final String MESSAGE_DUPLICATE_SYMBOLS = "Players must have different symbols";

    private int boardLength;
    private char player1Symbol;
    private char player2Symbol;
    private char computerSymbol;

    public Config(final Reader reader) throws InvalidConfigException {
        if (reader == null)
            throw new NullPointerException(MESSAGE_INPUT_STREAM_IS_NULL);

        Properties props = new Properties();

        try {
            props.load(reader);
        } catch (IOException ex) {
            throw new InvalidConfigException(MESSAGE_LOAD_ERROR);
        }

        try {
            String tmp = props.getProperty(ConfigKey.BOARD_LENGTH.name());

            if (tmp != null)
                boardLength = Integer.valueOf(tmp.trim());
        } catch (NumberFormatException ignored) {
        }

        if (boardLength < 3 || boardLength > 10)
            throw new InvalidConfigException(MESSAGE_INVALID_BOARD_LENGTH);

        player1Symbol = getSymbol(ConfigKey.PLAYER1_SYMBOL, props);
        player2Symbol = getSymbol(ConfigKey.PLAYER2_SYMBOL, props);
        computerSymbol = getSymbol(ConfigKey.COMPUTER_SYMBOL, props);

        if (player1Symbol == player2Symbol || player1Symbol == computerSymbol || player2Symbol == computerSymbol)
            throw new InvalidConfigException(MESSAGE_DUPLICATE_SYMBOLS);
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

    private char getSymbol(ConfigKey key, Properties props) throws InvalidConfigException {
        String tmp = props.getProperty(key.name());

        if (tmp != null) {
            tmp = tmp.trim();

            if (tmp.length() == 1)
                return tmp.charAt(0);
        }

        throw new InvalidConfigException(key + " must be 1 character");
    }
}
