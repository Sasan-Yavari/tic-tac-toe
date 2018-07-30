package com.metronom.tictactoe.entity;

import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

import java.awt.*;

public class Board {
    private static final String MESSAGE_INVALID_POINT = "Invalid point. Point must be between 1,1 and %d,%d.";
    private static final String MESSAGE_ALREADY_FULL = "This point is already full.";

    private static Board instance = new Board();

    private int freeRoomCount;
    private int boardLength;
    private Player[][] boardMatrix;

    private Board() {
    }

    public static Board getInstance() {
        return instance;
    }

    public void init(int boardLength) {
        this.boardLength = boardLength;
        this.boardMatrix = new Player[boardLength][boardLength];
        this.freeRoomCount = boardLength * boardLength;
    }

    public void put(Point point, Player player) throws InvalidCoordinateException {
        if (point.x < 0 || point.y < 0 || point.x >= boardLength || point.y >= boardLength)
            throw new InvalidCoordinateException(String.format(MESSAGE_INVALID_POINT, boardLength, boardLength));

        if (boardMatrix[point.x][point.y] != null)
            throw new InvalidCoordinateException(MESSAGE_ALREADY_FULL);

        boardMatrix[point.x][point.y] = player;
        freeRoomCount--;
    }

    public Player[][] getCopyOfBoardMatrix() {
        return boardMatrix.clone();
    }

    public int getFreeRoomCount() {
        return freeRoomCount;
    }
}
