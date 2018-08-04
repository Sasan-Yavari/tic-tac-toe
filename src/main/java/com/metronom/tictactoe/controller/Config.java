package com.metronom.tictactoe.controller;

import com.metronom.tictactoe.controller.enums.ConfigKey;
import com.metronom.tictactoe.exceptions.InvalidConfigException;
import com.metronom.tictactoe.lang.MessageKey;
import com.metronom.tictactoe.lang.Messages;

import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.Properties;

public class Config {
    private Messages messages = Messages.getInstance();
    private int boardLength;
    private char player1Symbol;
    private char player2Symbol;
    private char computerSymbol;

    /**
     * This constructor takes a {@link Reader} and if the <tt>reader</tt> is valid, instantiates <tt>Config</tt>
     * class and reads the config parameters from the <tt>reader</tt>.
     *
     * @param reader this is the source of the configs.
     * @throws InvalidConfigException if it fails to load configs from <tt>reader</tt> or
     * if there is a problem on any of the configs.
     */
    public Config(final Reader reader) throws InvalidConfigException {
        if (reader == null)
            throw new NullPointerException(messages.get(MessageKey.INPUT_STREAM_IS_NULL));

        Properties props = new Properties();

        try {
            props.load(reader);
        } catch (IOException ex) {
            throw new InvalidConfigException(messages.get(MessageKey.LOAD_ERROR));
        }

        // boardLength must be a number between 3 and 10 (both inclusive)
        boardLength = Optional.ofNullable(props.getProperty(ConfigKey.BOARD_LENGTH.name()))
                .map(String::trim)
                .filter(val -> val.matches("^[0-9]+$"))
                .map(Integer::valueOf)
                .filter(val -> val >= 3)
                .filter(val -> val <= 10)
                .orElseThrow(() -> new InvalidConfigException(messages.get(MessageKey.INVALID_BOARD_LENGTH)));

        player1Symbol = getSymbol(ConfigKey.PLAYER1_SYMBOL, props);
        player2Symbol = getSymbol(ConfigKey.PLAYER2_SYMBOL, props);
        computerSymbol = getSymbol(ConfigKey.COMPUTER_SYMBOL, props);

        if (player1Symbol == player2Symbol || player1Symbol == computerSymbol || player2Symbol == computerSymbol)
            throw new InvalidConfigException(messages.get(MessageKey.DUPLICATE_SYMBOLS));
    }

    /**
     * Returns the length of the board. The board is square and this methods output is the
     * width and the height of the board.
     *
     * @return length of the board. This is a number between 3 and 10.
     */
    public int getBoardLength() {
        return boardLength;
    }

    /**
     * Returns the Player1 character symbol that will be shown on the UI.
     *
     * @return character symbol of the Player1
     */
    public char getPlayer1Symbol() {
        return player1Symbol;
    }

    /**
     * Returns the Player2 character symbol that will be shown on the UI.
     *
     * @return character symbol of the Player2
     */
    public char getPlayer2Symbol() {
        return player2Symbol;
    }

    /**
     * Returns the Computer character symbol that will be shown on the UI.
     *
     * @return character symbol of the Computer
     */
    public char getComputerSymbol() {
        return computerSymbol;
    }

    /**
     * Returns the symbol of a player.
     * This method reads the given {@link ConfigKey} from the
     * given {@link Properties} and validates it as a player symbol.
     *
     * @param key   the name of parameter in properties
     * @param props the source of configs
     * @return symbol of a player
     * @throws InvalidConfigException if the symbol is not valid
     */
    private char getSymbol(final ConfigKey key, final Properties props) throws InvalidConfigException {
        // player symbol must be a character
        return Optional.ofNullable(props.getProperty(key.name()))
                .map(String::trim)
                .filter(val -> val.matches("^[a-zA-Z0-9]$"))
                .map(val -> val.charAt(0))
                .orElseThrow(() -> new InvalidConfigException(key + " must be 1 ASCII character"));
    }
}
