package com.metronom.tictactoe.business;

public class TicTacToe {
    private static TicTacToe instance = new TicTacToe();

    public static TicTacToe getInstance() {
        return instance;
    }

    private TicTacToe() {

    }
}
