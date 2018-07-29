package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.business.Game;

public interface IUserInterface {
    void init(Game game);
    void show();
    void showStatus();
    void showMessage(String message);
    void showError(String message);
}
