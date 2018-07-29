package com.metronom.tictactoe.business.entity;

import java.security.SecureRandom;

public class AIPlayer extends AbstractPlayer {
    private SecureRandom rnd = new SecureRandom();

    public AIPlayer(String name, char symbol) {
        super(name, symbol, false);
    }

    // TODO: Implement
    @Override
    public Point getNextMove(AbstractPlayer[][] boardMatrix) {
        Point point = new Point(rnd.nextInt(10), rnd.nextInt(10));
        System.out.println(getName() + " played " + point.getX() + "," + point.getY());
        return point;
    }
}
