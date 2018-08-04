package com.metronom.tictactoe.ui;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.controller.enums.GameStatus;
import com.metronom.tictactoe.entity.Coordinate;
import com.metronom.tictactoe.entity.Player;
import com.metronom.tictactoe.exceptions.InvalidCoordinateException;
import com.metronom.tictactoe.lang.MessageKey;
import com.metronom.tictactoe.lang.Messages;

import java.io.PrintStream;
import java.io.Reader;
import java.util.Scanner;

public class CommandLineUserInterface {
    private static CommandLineUserInterface instance = new CommandLineUserInterface();

    private Game game;
    private Messages messages = Messages.getInstance();
    private Scanner scanner;
    private PrintStream messagesOutput;
    private PrintStream errorsOutput;
    private String maxInputValue;

    private CommandLineUserInterface() {
    }

    public static CommandLineUserInterface getInstance() {
        return instance;
    }

    /**
     * Initializes the UI and displays the game banner.
     *
     * @param game        instance of {@link Game}
     * @param usersInput source of the user's input
     */
    public void show(Game game, Reader usersInput, PrintStream messagesOutput, PrintStream errorsOutput) {
        this.game = game;
        this.scanner = new Scanner(usersInput);
        this.messagesOutput = messagesOutput;
        this.errorsOutput = errorsOutput;
        this.maxInputValue = game.getConfig().getBoardLength() + "," + game.getConfig().getBoardLength();

        showMessage(String.format(messages.get(MessageKey.START_BANNER), game.getWinScore()));
        showConfigs();
    }

    /**
     * Starts the main loop for users interaction.
     * <br/><br/>
     * This method starts the game and inside a loop, takes the users input and
     * puts them inside the board using the {@link Game}'s {@code performAction} method.
     * After that, last move's data will be shown and the game status will be shown.
     * <br/><br/>
     * This loop runs while the game status is {@code GameStatus.RUNNING}
     */
    public void startGame() {
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

    /**
     * Writes a message to UI's message {@link PrintStream}
     * and waits for flush to avoid interference with other messages.
     *
     * @param message the message to show to the user
     */
    private void showMessage(String message) {
        messagesOutput.println(message);
        messagesOutput.flush();
        waitForFlush();
    }

    /**
     * Writes an error to UI's error {@link PrintStream}
     * and waits for flush to avoid interference with other messages.
     *
     * @param error the error to show to the user
     */
    private void showError(String error) {
        errorsOutput.println(error);
        errorsOutput.flush();
        waitForFlush();
    }

    /**
     * Simply sleeps for 10 milliseconds
     */
    private void waitForFlush() {
        try {
            Thread.sleep(10);
        } catch (Exception ignored) { }
    }

    /**
     * Shows the running game configs to user
     */
    private void showConfigs() {
        Config config = game.getConfig();
        showMessage(String.format(messages.get(MessageKey.CONFIGS), config.getBoardLength(), config.getPlayer1Symbol(), config.getPlayer2Symbol(), config.getComputerSymbol()));
    }

    /**
     * Shows the game status and the whole board to user
     */
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

    /**
     * Reads user input from command line interface. This method keeps prompting the user while
     * the input value is not valid.
     *
     * @param player the player that should give the next input
     * @return next {@link Coordinate} given by the player
     */
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
