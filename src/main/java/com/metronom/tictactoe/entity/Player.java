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
     * @param board copy of board
     * @return Decides about the next move based on board status.
     */
    public Optional<Coordinate> getNextMove(final Board board) {
        if (aiSupport) {
            int length = board.getBoardLength();

            for (int row = 0; row < length; row++) {
                for (int column = 0; column < length; column++) {
                    if (!board.getCell(row, column).isPresent())
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
