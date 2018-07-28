package com.metronom.tictactoe.business.players;

import com.metronom.tictactoe.business.entity.Point;

public abstract class AbstractPlayer {
    private String name;
    private char symbol;

    AbstractPlayer(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public abstract Point getNextMove(AbstractPlayer[][] boardMatrix);
}
