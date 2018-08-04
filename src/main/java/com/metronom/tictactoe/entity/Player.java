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
     * Accepts an instance of {@link Board} and calculates next move based on board status.
     * If this is not an automatic player, this method will return an {@code Optional.empty()}
     *
     * Note that, AI of this game is a dummy one and simply selects the first empty cell
     * by scanning the board from the top left point.
     *
     * @param board copy of board
     * @return the next move of the AI player or {@code Optional.empty()} for non-ai players.
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
