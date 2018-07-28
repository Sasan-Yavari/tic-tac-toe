package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.business.Board;

public class CommandLineUserInterface implements IUserInterface {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showError(String message) {
        System.err.println(message);
    }

    @Override
    public void update(Board board) {
    }
}
