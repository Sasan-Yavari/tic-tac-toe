package com.metronom.tictactoe.entity;

import com.metronom.tictactoe.exceptions.InvalidCoordinateException;

public class Board {
    private static final String MESSAGE_ALREADY_FULL = "This point is already full.";

    private static Board instance = new Board();

    private int freeRoomCount;
    private int boardLength;
    private Player[][] table;

    private Board() {
    }

    public static Board getInstance() {
        return instance;
    }

    public void init(int boardLength) {
        this.boardLength = boardLength;
        this.table = new Player[boardLength][boardLength];
        this.freeRoomCount = boardLength * boardLength;
    }

    public CellScore put(final Player player, final Coordinate coordinate) throws InvalidCoordinateException {
        coordinate.validate(0, boardLength);

        if (table[coordinate.row][coordinate.column] != null)
            throw new InvalidCoordinateException(MESSAGE_ALREADY_FULL);

        table[coordinate.row][coordinate.column] = player;
        freeRoomCount--;

        CellScore scores = new CellScore();
        scores.verticalScore = calculateVerticalScore(coordinate);
        scores.horizontalScore = calculateHorizontalScore(coordinate);
        scores.diagonal1Score = calculateDiagonal1Score(coordinate);
        scores.diagonal2Score = calculateDiagonal2Score(coordinate);

        return scores;
    }

    public Player[][] getCopyOfTable() {
        return table.clone();
    }

    public int getFreeRoomCount() {
        return freeRoomCount;
    }

    private Player getCell(int row, int column) {
        if (row >= 0 && column >= 0 && row < boardLength && column < boardLength)
            return table[row][column];

        return null;
    }

    private int calculateVerticalScore(final Coordinate coordinate) {
        int start = coordinate.row;
        int end = coordinate.row;

        final Player basePlayer = getCell(coordinate.row, coordinate.column);

        if (basePlayer != null) {
            while (start > 0) {
                if (basePlayer.equals(getCell(start - 1, coordinate.column))) {
                    start--;
                } else break;
            }

            while (end < boardLength - 1) {
                if (basePlayer.equals(getCell(end + 1, coordinate.column))) {
                    end++;
                } else break;
            }
        }

        return end - start + 1;
    }

    private int calculateHorizontalScore(final Coordinate coordinate) {
        int start = coordinate.column;
        int end = coordinate.column;

        final Player basePlayer = getCell(coordinate.row, coordinate.column);

        if (basePlayer != null) {
            while (start > 0) {
                if (basePlayer.equals(getCell(coordinate.row, start - 1))) {
                    start--;
                } else break;
            }

            while (end < boardLength - 1) {
                if (basePlayer.equals(getCell(coordinate.row, end + 1))) {
                    end++;
                } else break;
            }
        }

        return end - start + 1;
    }

    private int calculateDiagonal1Score(final Coordinate coordinate) {
        int startRow = coordinate.row;
        int startColumn = coordinate.column;
        int endRow = coordinate.row;
        int endColumn = coordinate.column;

        final Player basePlayer = getCell(coordinate.row, coordinate.column);

        if (basePlayer != null) {
            while (startRow > 0 && startColumn > 0) {
                if (basePlayer.equals(getCell(startRow - 1, startColumn - 1))) {
                    startRow--;
                    startColumn--;
                } else break;
            }

            while (endRow < boardLength - 1 && endColumn < boardLength - 1) {
                if (basePlayer.equals(getCell(endRow + 1, endColumn + 1))) {
                    endRow++;
                    endColumn++;
                } else break;
            }
        }

        return endColumn - startColumn + 1;
    }

    private int calculateDiagonal2Score(final Coordinate coordinate) {
        int startRow = coordinate.row;
        int startColumn = coordinate.column;
        int endRow = coordinate.row;
        int endColumn = coordinate.column;

        final Player basePlayer = getCell(coordinate.row, coordinate.column);

        if (basePlayer != null) {
            while (startRow < boardLength - 1 && startColumn > 0) {
                if (basePlayer.equals(getCell(startRow + 1, startColumn - 1))) {
                    startRow++;
                    startColumn--;
                } else break;
            }

            while (endRow > 0 && endColumn < boardLength - 1) {
                if (basePlayer.equals(getCell(endRow - 1, endColumn + 1))) {
                    endRow--;
                    endColumn++;
                } else break;
            }
        }

        return endColumn - startColumn + 1;
    }
}
