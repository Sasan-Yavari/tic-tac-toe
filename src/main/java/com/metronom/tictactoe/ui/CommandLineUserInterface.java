package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.Coordinate;
import com.metronom.tictactoe.entity.Player;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;
import com.metronom.tictactoe.lang.MessageKey;
import com.metronom.tictactoe.lang.Messages;

import java.io.InputStream;
import java.util.Scanner;

public class CommandLineUserInterface {
    private static CommandLineUserInterface instance = new CommandLineUserInterface();

    private Game game;
    private Messages messages = Messages.getInstance();
    private Scanner scanner;
    private String maxInputValue;

    private CommandLineUserInterface() {
    }

    public static CommandLineUserInterface getInstance() {
        return instance;
    }

    public void show(Game game, InputStream inputStream) {
        this.game = game;
        this.scanner = new Scanner(inputStream);
        this.maxInputValue = game.getConfig().getBoardLength() + "," + game.getConfig().getBoardLength();

        showMessage(String.format(messages.get(MessageKey.START_BANNER), game.getWinScore()));
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

                showMessage(String.format(messages.get(MessageKey.PLAYER_INPUT), player.getName(), player.getSymbol(), coordinate.row + 1, coordinate.column + 1));
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
        showMessage(String.format(messages.get(MessageKey.CONFIGS), config.getBoardLength(), config.getPlayer1Symbol(), config.getPlayer2Symbol(), config.getComputerSymbol()));
    }

    private void showStatus() {
        showMessage(String.format(messages.get(MessageKey.GAME_STATUS), game.getStatus().getMessage()));
        game.getWinner().ifPresent(winner -> showMessage(String.format(messages.get(MessageKey.WINNER), winner.getName(), winner.getSymbol())));
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
            showMessage(String.format(messages.get(MessageKey.ENTER_NEXT_POINT), player.getName(), player.getSymbol(), maxInputValue));

            response = scanner.nextLine();

            try {
                String[] parts = response.split(",");

                int row = Integer.valueOf(parts[0]) - 1;
                int column = Integer.valueOf(parts[1]) - 1;

                coordinate = new Coordinate(row, column);
            } catch (Exception ex) {
                showError(String.format(messages.get(MessageKey.WRONG_VALUE), response));
            }
        }

        return coordinate;
    }
}
