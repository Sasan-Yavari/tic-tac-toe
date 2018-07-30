package com.metronom.tictactoe;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.exceptions.ConfigNotFoundException;
import com.metronom.tictactoe.ui.CommandLineUserInterface;
import com.metronom.tictactoe.util.Util;

import java.io.File;
import java.io.FileReader;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToe {
    private static Logger logger = Logger.getLogger(TicTacToe.class.getName());

    public static void main(String[] args) {
        FileReader reader = null;

        try {
            File configFile = getConfigFile(args).orElseThrow(ConfigNotFoundException::new);

            reader = new FileReader(configFile);
            Config config = new Config(reader);
            reader.close();

            Game game = new Game(config);

            CommandLineUserInterface ui = CommandLineUserInterface.getInstance();
            ui.show(game);
            ui.startGame();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Fatal error", ex);
        } finally {
            Util.close(reader);
        }
    }

    private static Optional<File> getConfigFile(String[] args) {
        File file = null;

        if (args != null && args.length > 0 && args[0] != null) {
            file = new File(args[0]);
            if (!file.exists())
                file = null;
        }

        if (file == null) {
            file = new File(TicTacToe.class.getResource("../../../Config.properties").getFile());
            if (!file.exists())
                file = null;
        }

        return Optional.ofNullable(file);
    }
}
