package com.metronom.tictactoe;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.exceptions.ConfigNotFoundException;
import com.metronom.tictactoe.ui.CommandLineUserInterface;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToe {
    private static Logger logger = Logger.getLogger(TicTacToe.class.getName());

    public static void main(String[] args) {
        FileReader reader = null;

        try {
            File configFile = getConfigFile(args);

            reader = new FileReader(configFile);
            Config config = new Config(reader);
            reader.close();

            Game game = new Game(config);

            CommandLineUserInterface ui = CommandLineUserInterface.getInstance();
            ui.show(game, System.in);
            ui.startGame();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Fatal error", ex);
        } finally {
            close(reader);
        }
    }

    private static void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception ignored) {
            }
        }
    }

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
