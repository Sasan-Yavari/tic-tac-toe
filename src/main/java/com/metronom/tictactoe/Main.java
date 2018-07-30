package com.metronom.tictactoe;

import com.metronom.tictactoe.controller.Config;
import com.metronom.tictactoe.controller.Game;
import com.metronom.tictactoe.ui.CommandLineUserInterface;

import java.io.File;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            File configFile = new File(Main.class.getResource("../../../Config.properties").getFile());
            Config config = new Config(new FileReader(configFile));

            Game game = Game.getInstance();
            game.init(config);

            CommandLineUserInterface ui = CommandLineUserInterface.getInstance();
            ui.show(game);
            ui.startGame();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
