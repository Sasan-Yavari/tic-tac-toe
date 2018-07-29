package com.metronom.tictactoe;

import com.metronom.tictactoe.business.Game;
import com.metronom.tictactoe.business.entity.Config;
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
            ui.init(game);
            ui.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
