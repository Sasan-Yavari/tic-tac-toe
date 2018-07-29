package com.metronom.tictactoe.business.entity;

public abstract class AbstractPlayer {
    private String name;
    private char symbol;
    private boolean external;

    AbstractPlayer(String name, char symbol, boolean external) {
        this.name = name;
        this.symbol = symbol;
        this.external = external;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public boolean isExternal() {
        return external;
    }

    public Point getNextMove(AbstractPlayer[][] boardMatrix) {
        return null;
    }
}
