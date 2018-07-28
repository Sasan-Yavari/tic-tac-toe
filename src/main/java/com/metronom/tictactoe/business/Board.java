package com.metronom.tictactoe.business;

import com.metronom.tictactoe.business.entity.Point;
import com.metronom.tictactoe.business.players.AbstractPlayer;

public class Board {
    private static final int MAX_WIN_LENGTH = 5;

    private int freeRoomCount;
    private int boardLength;
    private int winLength;
    private AbstractPlayer[][] boardMatrix;

    public Board(int boardLength) {
        this.boardLength = boardLength;
        this.winLength = Math.min(boardLength, MAX_WIN_LENGTH);
        this.boardMatrix = new AbstractPlayer[boardLength][boardLength];
        this.freeRoomCount = boardLength * boardLength;
    }

    public void put(Point point, AbstractPlayer player) throws InvalidCoordinateException {
        validateCoordinates(point);
        boardMatrix[point.getX()][point.getY()] = player;
        freeRoomCount--;
    }

    public AbstractPlayer[][] getCopyOfBoardMatrix() {
        return boardMatrix.clone();
    }

    public int getFreeRoomCount() {
        return freeRoomCount;
    }

    private void validateCoordinates(Point point) throws InvalidCoordinateException {
        if (point.getX() < 0 || point.getY() < 0 || point.getX() >= boardLength || point.getY() >= boardLength)
            throw new InvalidCoordinateException("Invalid point " + point.toString() + ". Coordinate must be between 1,1 and " + boardLength + "," + boardLength + ".");

        if (boardMatrix[point.getX()][point.getY()] != null)
            throw new InvalidCoordinateException("Invalid point " + point.toString() + ". This point is already full.");
    }
}
