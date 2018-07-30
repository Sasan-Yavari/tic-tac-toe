package com.metronom.tictactoe.entity;

public abstract class AbstractPlayer {
    private String name;
    private char symbol;
    private boolean automatic;

    AbstractPlayer(String name, char symbol, boolean automatic) {
        this.name = name;
        this.symbol = symbol;
        this.automatic = automatic;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    /**
     * Accepts a copy of board data and calculates next move based on board status.
     *
     * @param boardMatrix copy of board data
     * @return {@code null} if not implemented or an instance of {@link Point}
     */
    public Point getNextMove(AbstractPlayer[][] boardMatrix) {
        return null;
    }
}
