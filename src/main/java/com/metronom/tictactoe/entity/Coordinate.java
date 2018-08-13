package com.metronom.tictactoe.entity;

public class Coordinate {
    public final int row;
    public final int column;

    public Coordinate(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isThisACorner(final int boardLenth) {
        return (row == 0 && column == 0
                || row == 0 && column == boardLenth - 1
                || row == boardLenth - 1 && column == 0
                || row == boardLenth - 1 && column == boardLenth - 1);
    }
}
