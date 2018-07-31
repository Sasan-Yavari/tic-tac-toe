package com.metronom.tictactoe.entity;

import java.util.Optional;

public class Player {
    private String name;
    private char symbol;
    private boolean aiSupport;

    Player(String name, char symbol, boolean aiSupport) {
        this.name = name;
        this.symbol = symbol;
        this.aiSupport = aiSupport;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    /**
     * Accepts a copy of board data and calculates next move based on board status.
     *
     * @param table copy of board data
     * @return Decides about the next move based on board status.
     */
    public Optional<Coordinate> getNextMove(Player[][] table) {

        if (aiSupport) {
            for (int row = 0; row < table.length; row++) {
                for (int column = 0; column < table[row].length; column++) {
                    if (table[row][column] == null)
                        return Optional.of(new Coordinate(row, column));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player && ((Player) obj).symbol == symbol;
    }

    @Override
    public int hashCode() {
        return symbol;
    }
}
