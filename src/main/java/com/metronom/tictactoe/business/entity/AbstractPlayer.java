package com.metronom.tictactoe.business.entity;

public abstract class AbstractPlayer {
    private String name;
    private char symbol;
    private boolean automatic;

    AbstractPlayer(String name, char symbol, boolean automatic) {
        this.name = name;
        this.symbol = symbol;
        this.automatic = automatic;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public Point getNextMove(AbstractPlayer[][] boardMatrix) {
        return null;
    }
}
