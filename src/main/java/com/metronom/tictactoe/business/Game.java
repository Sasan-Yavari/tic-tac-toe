package com.metronom.tictactoe.business;

import com.metronom.tictactoe.business.players.AbstractPlayer;
import com.metronom.tictactoe.ui.IUserInterface;

public class Game {
    private int turn;

    private Board board;
    private IUserInterface ui;
    private AbstractPlayer[] players;

    public Game(Board board, IUserInterface ui, AbstractPlayer player1, AbstractPlayer player2, AbstractPlayer ai) {
        this.board = board;
        this.ui = ui;

        this.players = new AbstractPlayer[3];
        this.players[0] = player1;
        this.players[1] = player2;
        this.players[2] = ai;
        this.turn = 0;
    }

    public void start() {
        PlayResult result = PlayResult.DONE;

        while (result == PlayResult.DONE) {
            try {
                AbstractPlayer player = players[turn];
                board.put(player.getNextMove(board.getCopyOfBoardMatrix()), player);
                result = checkStatus();

                turn++;

                if (turn == players.length)
                    turn = 0;

                ui.update(board);
            } catch (InvalidCoordinateException ex) {
                ui.showError(ex.getMessage());
            }
        }

        ui.showMessage(result.getMessage());
    }

    // TODO implement
    private PlayResult checkStatus() {
        PlayResult result = PlayResult.DONE;

        if (board.getFreeRoomCount() == 0)
            result = PlayResult.GAME_OVER;

        return result;
    }
}
