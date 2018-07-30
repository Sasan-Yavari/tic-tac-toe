package com.metronom.tictactoe.entity;

public class PlayerFactory {
    /**
     * @param name      name of the player
     * @param symbol    character symbol of the player
     * @param automatic Is this an automatic player or not
     * @return instance of {@link AIPlayer} or {@link HumanPlayer} according to value of
     * <tt>automatic</tt>
     */
    public static AbstractPlayer createPlayer(String name, char symbol, boolean automatic) {
        if (automatic) return new AIPlayer(name, symbol);
        else return new HumanPlayer(name, symbol);
    }
}
