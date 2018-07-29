package com.metronom.tictactoe.business.entity;

import com.metronom.tictactoe.business.exceptions.InvalidCoordinateException;

public class Board {
    private static Board instance = new Board();

    private int freeRoomCount;
    private int boardLength;
    private AbstractPlayer[][] boardMatrix;

    private Board() { }

    public static Board getInstance() {
        return instance;
    }

    public void init(int boardLength) {
        this.boardLength = boardLength;
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
            throw new InvalidCoordinateException("Invalid point. Point must be between 1,1 and " + boardLength + "," + boardLength + ".");

        if (boardMatrix[point.getX()][point.getY()] != null)
            throw new InvalidCoordinateException("This point is already full.");
    }
}
