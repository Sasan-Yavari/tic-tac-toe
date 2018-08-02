package com.metronom.tictactoe.entity;

import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

import java.util.Optional;

public class Board {
    private static final String MESSAGE_INVALID_POINT = "Invalid coordinate. Numbers must be between 1,1 and %d,%d.";
    private static final String MESSAGE_ALREADY_FULL = "This point is already full.";

    private static Board instance = new Board();

    private int freeRoomCount;
    private int boardLength;
    private Character[][] table;

    private Board() {
    }

    public static Board getInstance() {
        return instance;
    }

    public void init(int boardLength) {
        this.boardLength = boardLength;
        this.table = new Character[boardLength][boardLength];
        this.freeRoomCount = boardLength * boardLength;
    }

    public void put(final Character value, final Coordinate coordinate) throws InvalidCoordinateException {
        if (!isInRange(coordinate.row, coordinate.column))
            throw new InvalidCoordinateException(String.format(MESSAGE_INVALID_POINT, boardLength, boardLength));

        if (table[coordinate.row][coordinate.column] != null)
            throw new InvalidCoordinateException(MESSAGE_ALREADY_FULL);

        table[coordinate.row][coordinate.column] = value;
        freeRoomCount--;
    }

    public int getFreeRoomCount() {
        return freeRoomCount;
    }

    public int getBoardLength() {
        return boardLength;
    }

    public int calculateHorizontalScore(final Coordinate coordinate) {
        return calculateScore(coordinate, 0, -1, 0, 1);
    }

    public int calculateVerticalScore(final Coordinate coordinate) {
        return calculateScore(coordinate, -1, 0, 1, 0);
    }

    public int calculateDiagonal1Score(final Coordinate coordinate) {
        return calculateScore(coordinate, -1, -1, 1, 1);
    }

    public int calculateDiagonal2Score(final Coordinate coordinate) {
        return calculateScore(coordinate, -1, 1, 1, -1);
    }

    public Optional<Character> getCell(final int row, final int column) {
        if (row >= 0 && column >= 0 && row < boardLength && column < boardLength)
            return Optional.ofNullable(table[row][column]);

        return Optional.empty();
    }

    private boolean isInRange(int row, int column) {
        return row >= 0 && column >= 0 && row < boardLength && column < boardLength;
    }

    private int calculateScore(final Coordinate coordinate,
                               final int startRowInc,
                               final int startColumnInc,
                               final int endRowInc,
                               final int endColumnInc) {
        int startRow = coordinate.row;
        int startColumn = coordinate.column;
        int endRow = coordinate.row;
        int endColumn = coordinate.column;

        final Optional<Character> cellValue = getCell(coordinate.row, coordinate.column);

        if (cellValue.isPresent()) {
            while (isInRange(startRow, startColumn)) {
                if (cellValue.equals(getCell(startRow + startRowInc, startColumn + startColumnInc))) {
                    startRow += startRowInc;
                    startColumn += startColumnInc;
                } else break;
            }

            while (isInRange(endRow, endColumn)) {
                if (cellValue.equals(getCell(endRow + endRowInc, endColumn + endColumnInc))) {
                    endRow += endRowInc;
                    endColumn += endColumnInc;
                } else break;
            }
        }

        return Math.max(endRow - startRow + 1, endColumn - startColumn + 1);
    }
}
