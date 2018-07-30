package com.metronom.tictactoe.entity;

import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

public class Board {
    private static final String MESSAGE_INVALID_POINT = "Invalid point. Point must be between 1,1 and %d,%d.";
    private static final String MESSAGE_ALREADY_FULL = "This point is already full.";

    private static Board instance = new Board();

    private int freeRoomCount;
    private int boardLength;
    private AbstractPlayer[][] boardMatrix;

    private Board() {
    }

    public static Board getInstance() {
        return instance;
    }

    public void init(int boardLength) {
        this.boardLength = boardLength;
        this.boardMatrix = new AbstractPlayer[boardLength][boardLength];
        this.freeRoomCount = boardLength * boardLength;
    }

    public void put(Point point, AbstractPlayer player) throws InvalidCoordinateException {
        if (point.getX() < 0 || point.getY() < 0 || point.getX() >= boardLength || point.getY() >= boardLength)
            throw new InvalidCoordinateException(String.format(MESSAGE_INVALID_POINT, boardLength, boardLength));

        if (boardMatrix[point.getX()][point.getY()] != null)
            throw new InvalidCoordinateException(MESSAGE_ALREADY_FULL);

        boardMatrix[point.getX()][point.getY()] = player;
        freeRoomCount--;
    }

    public AbstractPlayer[][] getCopyOfBoardMatrix() {
        return boardMatrix.clone();
    }

    public int getFreeRoomCount() {
        return freeRoomCount;
    }
}
