package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.entity.AbstractPlayer;
import com.metronom.tictactoe.entity.Point;
import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

import java.util.Scanner;

public class CommandLineUserInterface implements IUserInterface {
    private static final String MESSAGE_BANNER = "Tic Tac Toe 2.0 Started" +
            "Author: Sasan Yavari" +
            "This is a 3 player game. Player1 and Player2 are humans and 3rd player is computer.";

    private static final String MESSAGE_CONFIGS = "Configs:\n" +
            "\tBoard Length: %d\n" +
            "\tPlayer1 Symbol: %c\n" +
            "\tPlayer2 Symbol: %c\n" +
            "\tComputer Symbol: %c";

    private static final String MESSAGE_ENTER_NEXT_POINT = "%s (%c) enter the next x,y between 1,1 and %s:";
    private static final String MESSAGE_WRONG_VALUE = "Wrong value: %s";
    private static final String MESSAGE_PLAYER_INPUT = "%s (%c) played %d,%d";
    private static final String MESSAGE_GAME_STATUS = "Game status: %s";

    private static CommandLineUserInterface instance = new CommandLineUserInterface();

    private Game game;
    private Scanner scanner = new Scanner(System.in);
    private String maxInputValue;

    private CommandLineUserInterface() {
    }

    public static CommandLineUserInterface getInstance() {
        return instance;
    }

    @Override
    public void show(Game game) {
        this.game = game;
        this.maxInputValue = game.getConfig().getBoardLength() + "," + game.getConfig().getBoardLength();

        showMessage(MESSAGE_BANNER);
        showConfigs();
    }

    @Override
    public void startGame() {
        game.start();

        while (game.getStatus() == GameStatus.RUNNING) {
            AbstractPlayer player = game.getNextPlayer();

            try {
                Point point = readNextInput(player);
                game.actionPerformed(point);

                showMessage(String.format(MESSAGE_PLAYER_INPUT, player.getName(), player.getSymbol(), point.getX() + 1, point.getY() + 1));
                showStatus();
            } catch (InvalidCoordinateException ex) {
                showError(ex.getMessage());
            }
        }
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

    private void showError(String message) {
        System.err.println(message);
        System.err.flush();
        waitToFlush();
    }

    private void showConfigs() {
        Config config = game.getConfig();
        showMessage(String.format(MESSAGE_CONFIGS, config.getBoardLength(), config.getPlayer1Symbol(), config.getPlayer2Symbol(), config.getComputerSymbol()));
    }

    private void showStatus() {
        showMessage(String.format(MESSAGE_GAME_STATUS, game.getStatus().getMessage()));
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

    private void waitToFlush() {
        try {
            Thread.sleep(10);
        } catch (Exception ignored) {
        }
    }

    private Point readNextInput(AbstractPlayer player) throws InvalidCoordinateException {
        Point point = null;
        String response;

        while (point == null) {
            if (player.isAutomatic()) {
                point = player.getNextMove(game.getCopyOfBoardMatrix());
            } else {
                showMessage(String.format(MESSAGE_ENTER_NEXT_POINT, player.getName(), player.getSymbol(), maxInputValue));

                response = scanner.nextLine();

                try {
                    String[] parts = response.split(",");

                    int x = Integer.valueOf(parts[0]) - 1;
                    int y = Integer.valueOf(parts[1]) - 1;

                    point = new Point(x, y);
                } catch (Exception ex) {
                    throw new InvalidCoordinateException(String.format(MESSAGE_WRONG_VALUE, response));
                }
            }
        }

        return point;
    }
}
