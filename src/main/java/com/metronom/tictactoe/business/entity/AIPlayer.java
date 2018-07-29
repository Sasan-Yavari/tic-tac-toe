package com.metronom.tictactoe.business.entity;

import java.security.SecureRandom;

public class AIPlayer extends AbstractPlayer {
    private SecureRandom rnd = new SecureRandom();

    public AIPlayer(String name, char symbol) {
        super(name, symbol, true);
    }

    // TODO: Implement
    @Override
    public Point getNextMove(AbstractPlayer[][] boardMatrix) {
        return new Point(rnd.nextInt(10), rnd.nextInt(10));
    }
}
