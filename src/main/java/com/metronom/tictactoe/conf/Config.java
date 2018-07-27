package com.metronom.tictactoe.conf;

public class Config {
    private static final Config instance = new Config();

    private int boardLength = 3;
    private char player1Symbol = 'x';
    private char player2Symbol = 'o';
    private char computerSymbol = 'c';

    public static Config getInstance() {
        return instance;
    }

    private Config() { }

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
