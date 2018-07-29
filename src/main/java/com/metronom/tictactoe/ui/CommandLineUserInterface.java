package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.business.Game;
import com.metronom.tictactoe.business.entity.AbstractPlayer;
import com.metronom.tictactoe.business.entity.Point;
import com.metronom.tictactoe.business.enums.GameStatus;
import com.metronom.tictactoe.business.exceptions.InvalidCoordinateException;

import java.util.Scanner;

public class CommandLineUserInterface implements IUserInterface {
    private static CommandLineUserInterface instance = new CommandLineUserInterface();
    private Game game;

    private CommandLineUserInterface() {
    }

    public static CommandLineUserInterface getInstance() {
        return instance;
    }

    @Override
    public void init(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        showMessage("Tic Tac Toe 2.0 Started");
        showMessage("Author: Sasan Yavari");
        showMessage("This is a 3 player game. Player1 and Player2 are human and 3rd player is computer.");
        showMessage("Configs: " + game.getConfig().toString());

        String maxInputValue = game.getConfig().getBoardLength() + "," + game.getConfig().getBoardLength();
        Scanner scanner = new Scanner(System.in);
        String response;

        game.start();

        while (game.getStatus() == GameStatus.RUNNING) {
            AbstractPlayer player = game.getNextPlayer();
            Point point = null;

            try {
                while (point == null) {
                    if (player.isExternal()) {
                        showMessage(player.getName() + "(" + player.getSymbol() + ") enter the next x,y between 1,1 and " + maxInputValue + ":");

                        response = scanner.nextLine();

                        try {
                            String[] parts = response.split(",");

                            int x = Integer.valueOf(parts[0]) - 1;
                            int y = Integer.valueOf(parts[1]) - 1;

                            point = new Point(x, y);
                        } catch (Exception ex) {
                            throw new InvalidCoordinateException("Wrong value: " + response);
                        }
                    } else point = player.getNextMove(game.getCopyOfBoardMatrix());
                }

                game.play(point);
            } catch (InvalidCoordinateException ex) {
                showError(ex.getMessage());
            }
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void showError(String message) {
        System.err.println(message);
    }

    @Override
    public void update() {
        showMessage("Game status is " + game.getStatus().getMessage());
        AbstractPlayer[][] boardMatrix = game.getCopyOfBoardMatrix();
        StringBuilder sb = new StringBuilder();

        for (AbstractPlayer[] row : boardMatrix) {
            sb.append("|");

            for (AbstractPlayer cell : row)
                sb.append(cell == null ? " " : cell.getSymbol()).append("|");

            sb.append("\n");
        }

        showMessage(sb.toString());
    }
}
