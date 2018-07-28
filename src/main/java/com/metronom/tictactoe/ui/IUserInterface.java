package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.business.Board;

public interface IUserInterface {
    void showMessage(String message);
    void showError(String message);
    void update(Board board);
}
