package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.ConfigKey;
import com.metronom.tictactoe.exceptions.InvalidConfigException;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
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

        boardLength = Optional.ofNullable(props.getProperty(ConfigKey.BOARD_LENGTH.name()))
                .map(String::trim)
                .filter(val -> val.matches("^[0-9]+$"))
                .map(Integer::valueOf)
                .filter(val -> val >= 3)
                .filter(val -> val <= 10)
                .orElseThrow(() -> new InvalidConfigException(MESSAGE_INVALID_BOARD_LENGTH));

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

    private char getSymbol(final ConfigKey key, final Properties props) throws InvalidConfigException {
        return Optional.ofNullable(props.getProperty(key.name()))
                .map(String::trim)
                .filter(val -> val.matches("^[a-zA-Z0-9]$"))
                .map(val -> val.charAt(0))
                .orElseThrow(() -> new InvalidConfigException(key + " must be 1 ASCII character"));
    }
}
