package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.Coordinate;
import com.metronom.tictactoe.entity.Player;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

import java.util.Scanner;

public class CommandLineUserInterface {
    private static final String MESSAGE_BANNER = "Tic Tac Toe 2.0 Started\n" +
            "Author: Sasan Yavari\n" +
            "This is a 3 player game. Player1 and Player2 are humans and 3rd player is computer.";

    private static final String MESSAGE_CONFIGS = "Configs:\n" +
            "\tBoard Length: %d\n" +
            "\tPlayer1 Symbol: %c\n" +
            "\tPlayer2 Symbol: %c\n" +
            "\tComputer Symbol: %c";

    private static final String MESSAGE_ENTER_NEXT_POINT = "%s (%c) enter the next row,column between 1,1 and %s:";
    private static final String MESSAGE_WRONG_VALUE = "Wrong value: %s";
    private static final String MESSAGE_PLAYER_INPUT = "%s (%c) played %d,%d";
    private static final String MESSAGE_GAME_STATUS = "Game status: %s";
    private static final String MESSAGE_WINNER = "Winner is %s (%c)";

    private static CommandLineUserInterface instance = new CommandLineUserInterface();

    private Game game;
    private Scanner scanner = new Scanner(System.in);
    private String maxInputValue;

    private CommandLineUserInterface() {
    }

    public static CommandLineUserInterface getInstance() {
        return instance;
    }

    public void show(Game game) {
        this.game = game;
        this.maxInputValue = game.getConfig().getBoardLength() + "," + game.getConfig().getBoardLength();

        showMessage(MESSAGE_BANNER);
        showConfigs();
    }

    public void startGame() {
        game.start();
        showStatus();

        while (game.getStatus() == GameStatus.RUNNING) {
            Player player = game.getNextPlayer();

            try {
                Coordinate coordinate = player.getNextMove(game.getBoard()).orElseGet(() -> readFromCLI(player));

                game.performAction(coordinate);

                showMessage(String.format(MESSAGE_PLAYER_INPUT, player.getName(), player.getSymbol(), coordinate.row + 1, coordinate.column + 1));
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
        game.getWinner().ifPresent(winner -> showMessage(String.format(MESSAGE_WINNER, winner.getName(), winner.getSymbol())));
        StringBuilder sb = new StringBuilder();

        for (int row = -1; row < game.getBoard().getBoardLength(); row++) {
            for (int column = -1; column < game.getBoard().getBoardLength(); column++) {
                if (row == -1 && column > -1)
                    sb.append(column + 1);
                else if (column == -1 && row > -1)
                    sb.append(row + 1);
                else sb.append(game.getBoard().getCell(row, column).orElse(' '));

                sb.append("|");
            }

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

    private Coordinate readFromCLI(Player player) {
        Coordinate coordinate = null;
        String response;

        while (coordinate == null) {
            showMessage(String.format(MESSAGE_ENTER_NEXT_POINT, player.getName(), player.getSymbol(), maxInputValue));

            response = scanner.nextLine();

            try {
                String[] parts = response.split(",");

                int row = Integer.valueOf(parts[0]) - 1;
                int column = Integer.valueOf(parts[1]) - 1;

                coordinate = new Coordinate(row, column);
            } catch (Exception ex) {
                showError(String.format(MESSAGE_WRONG_VALUE, response));
            }
        }

        return coordinate;
    }
}
