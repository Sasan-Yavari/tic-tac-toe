package com.metronom.tictactoe;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.exceptions.ConfigNotFoundException;
import com.metronom.tictactoe.ui.CommandLineUserInterface;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToe {
    private static Logger logger = Logger.getLogger(TicTacToe.class.getName());

    /**
     * Entrance point of the program.
     * Initializes everything and starts the game.
     *
     * @param args program input arguments
     */
    public static void main(String[] args) {
        FileReader reader = null;

        try {
            File configFile = getConfigFile(args);

            reader = new FileReader(configFile);
            Config config = new Config(reader);
            reader.close();

            Game game = new Game(config);

            CommandLineUserInterface ui = CommandLineUserInterface.getInstance();
            ui.show(game, new InputStreamReader(System.in), System.out, System.err);
            ui.startGame();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Fatal error", ex);
        } finally {
            close(reader);
        }
    }

    /**
     * Simply closes the given {@link Reader}
     *
     * @param reader a {@code reader} to close
     */
    private static void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Checks the program arguments to find a path for a file. If fails to find one, tries to load
     * default config file. If fails, throws a {@link ConfigNotFoundException}
     *
     * @param args program input arguments
     * @return instance of {@link File}
     * @throws ConfigNotFoundException if fails to find a config file
     */
    private static File getConfigFile(String[] args) throws ConfigNotFoundException {
        File file = null;

        if (args != null && args.length > 0 && args[0] != null) {
            file = new File(args[0]);
            if (!file.exists())
                file = null;
        }

        if (file == null) {
            URL url = TicTacToe.class.getResource("../../../config.properties");

            if (url != null) {
                file = new File(url.getFile());
                if (!file.exists())
                    file = null;
            }
        }

        if (file == null)
            throw new ConfigNotFoundException();

        return file;
    }
}
