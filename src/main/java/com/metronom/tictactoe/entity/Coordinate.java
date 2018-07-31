package com.metronom.tictactoe.entity;

import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

public class Coordinate {
    private static final String MESSAGE_INVALID_POINT = "Invalid coordinate. Numbers must be between 1,1 and %d,%d.";

    public final int row;
    public final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void validate(int min, int max) throws InvalidCoordinateException {
        if (row < min || column < min || row > max || column > max)
            throw new InvalidCoordinateException(String.format(MESSAGE_INVALID_POINT, max, max));
    }
}
