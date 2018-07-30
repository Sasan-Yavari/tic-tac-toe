package com.metronom.tictactoe.entity;

class AIPlayer extends AbstractPlayer {
    AIPlayer(String name, char symbol) {
        super(name, symbol, true);
    }

    /**
     * Accepts a copy of board data and calculates next move based on board status.
     *
     * @param boardMatrix copy of board data
     * @return Decides about the next move based on board status.
     */
    @Override
    public Point getNextMove(AbstractPlayer[][] boardMatrix) {
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == null)
                    return new Point(i, j);
            }
        }

        return null;
    }
}
