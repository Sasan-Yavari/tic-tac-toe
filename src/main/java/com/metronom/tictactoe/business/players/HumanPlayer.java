package com.metronom.tictactoe.business.players;

import com.metronom.tictactoe.business.entity.Point;

import java.security.SecureRandom;

public class HumanPlayer extends AbstractPlayer {
    private SecureRandom rnd = new SecureRandom();

    public HumanPlayer(String name, char symbol) {
        super(name, symbol);
    }

    // TODO: Implement
    @Override
    public Point getNextMove(AbstractPlayer[][] boardMatrix) {
        try {
            Thread.sleep(100);
        } catch (Exception ex) {

        }
        Point point = new Point(rnd.nextInt(10), rnd.nextInt(10));
        System.out.println(getName() + " played " + point.getX() + "," + point.getY());
        return point;
    }
}
