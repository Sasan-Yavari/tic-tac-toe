package com.metronom.tictactoe.entity;

import java.awt.*;
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
     * @param boardMatrix copy of board data
     * @return Decides about the next move based on board status.
     */
    public Optional<Point> getNextMove(Player[][] boardMatrix) {
        if (aiSupport) {
            for (int i = 0; i < boardMatrix.length; i++) {
                for (int j = 0; j < boardMatrix[i].length; j++) {
                    if (boardMatrix[i][j] == null)
                        return Optional.of(new Point(i, j));
                }
            }
        }

        return Optional.empty();
    }
}
