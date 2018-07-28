package com.metronom.tictactoe;

import com.metronom.tictactoe.business.Board;
import com.metronom.tictactoe.business.Game;
import com.metronom.tictactoe.business.players.AIPlayer;
import com.metronom.tictactoe.business.players.HumanPlayer;
import com.metronom.tictactoe.conf.Config;
import com.metronom.tictactoe.ui.CommandLineUserInterface;

import java.io.File;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            File configFile = new File(Main.class.getResource("../../../Config.properties").getFile());
            Config config = new Config(new FileReader(configFile));

            HumanPlayer player1 = new HumanPlayer("Player1", config.getPlayer1Symbol());
            HumanPlayer player2 = new HumanPlayer("Player2", config.getPlayer2Symbol());
            AIPlayer playerAi = new AIPlayer("Computer", config.getComputerSymbol());
            Board board = new Board(config.getBoardLength());
            CommandLineUserInterface ui = new CommandLineUserInterface();
            Game game = new Game(board, ui, player1, player2, playerAi);

            game.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
