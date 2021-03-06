package com.metronom.tictactoe.entity;

import com.metronom.tictactoe.exceptions.InvalidCoordinateException;
import com.metronom.tictactoe.lang.MessageKey;
import com.metronom.tictactoe.lang.Messages;

import java.util.Optional;

public class Board {
    private static Board instance = new Board();

    private Messages messages = Messages.getInstance();
    private Character[][] table;
    private int freeRoomCount;
    private int boardLength;

    private Board() {
    }

    public static Board getInstance() {
        return instance;
    }

    /**
     * Initializes the board by setting the {@code boardLength} and instantiating the board {@code table} and setting
     * the {@code freeRoomCount}.
     *
     * @param boardLength length of the board
     */
    public void init(int boardLength) {
        this.boardLength = boardLength;
        this.table = new Character[boardLength][boardLength];
        this.freeRoomCount = boardLength * boardLength;
    }

    /**
     * Puts a character in the {@code coordinate} location of the board.
     *
     * @param value      the character to put on board
     * @param coordinate location to put the character
     * @throws InvalidCoordinateException if the {@code coordinate} is not in the boards length or the cell
     *                                    is already full.
     */
    public void put(final Character value, final Coordinate coordinate) throws InvalidCoordinateException {
        if (!isInRange(coordinate.row, coordinate.column))
            throw new InvalidCoordinateException(String.format(messages.get(MessageKey.INVALID_POINT), boardLength, boardLength));

        if (table[coordinate.row][coordinate.column] != null)
            throw new InvalidCoordinateException(messages.get(MessageKey.CELL_ALREADY_FULL));

        table[coordinate.row][coordinate.column] = value;
        freeRoomCount--;
    }

    /**
     * Gives the free room count in the board.
     *
     * @return {@code freeRoomCount}
     */
    public int getFreeRoomCount() {
        return freeRoomCount;
    }

    /**
     * Gives the board length.
     *
     * @return {@code boardLength}
     */
    public int getBoardLength() {
        return boardLength;
    }

    /**
     * Calculates the horizontal score of the given coordinate. Assume that we have a {@code x}
     * at the location {@code {0, 0}} and we have another {@code x} at the location {@code {0, 1}}.
     * In this case, this method will return {@code 2} for both coordinates.
     *
     * @param coordinate the location of the board that we want to calculate it's horizontal score.
     * @return score of the given coordinate.
     */
    public int calculateHorizontalScore(final Coordinate coordinate) {
        return calculateScore(coordinate, 0, -1, 0, 1);
    }

    /**
     * Calculates the vertical score of the given coordinate. Assume that we have a {@code x}
     * at the location {@code {0, 0}} and we have another {@code x} at the location {@code {1, 0}}.
     * In this case, this method will return {@code 2} for both coordinates.
     *
     * @param coordinate the location of the board that we want to calculate it's vertical score.
     * @return score of the given coordinate.
     */
    public int calculateVerticalScore(final Coordinate coordinate) {
        return calculateScore(coordinate, -1, 0, 1, 0);
    }

    /**
     * Calculates the diagonal1 score of the given coordinate. Assume that we have a {@code x}
     * at the location {@code {0, 0}} and we have another {@code x} at the location {@code {1, 1}}.
     * In this case, this method will return {@code 2} for both coordinates.
     *
     * @param coordinate the location of the board that we want to calculate it's diagonal1 score.
     * @return score of the given coordinate.
     */
    public int calculateDiagonal1Score(final Coordinate coordinate) {
        return calculateScore(coordinate, -1, -1, 1, 1);
    }

    /**
     * Calculates the diagonal2 score of the given coordinate. Assume that we have a {@code x}
     * at the location {@code {0, 1}} and we have another {@code x} at the location {@code {1, 0}}.
     * In this case, this method will return {@code 2} for both coordinates.
     *
     * @param coordinate the location of the board that we want to calculate it's diagonal2 score.
     * @return score of the given coordinate.
     */
    public int calculateDiagonal2Score(final Coordinate coordinate) {
        return calculateScore(coordinate, -1, 1, 1, -1);
    }

    /**
     * Gives the value of the given coordinate as an {@code Optional<Character>}.
     * This method will return {@code Optional.empty} if the location is empty or
     * if the given coordinate is not in the range of the board.
     *
     * @param row    row number of the board
     * @param column column number of the board
     * @return {@code Optional<Character>}
     */
    public Optional<Character> getCell(final int row, final int column) {
        if (isInRange(row, column))
            return Optional.ofNullable(table[row][column]);

        return Optional.empty();
    }

    /**
     * Indicates that, the given coordinate is inside the board or not.
     *
     * @param row    row number
     * @param column column number
     * @return {@code true} if the coordinate is inside the board and otherwise {@code false}
     */
    private boolean isInRange(int row, int column) {
        return row >= 0 && column >= 0 && row < boardLength && column < boardLength;
    }

    /**
     * This is the main method for calculating the score of a cell in any direction.
     * We can specify the direction using the input parameters.
     * <br/><br/>
     * We have 4 {@code int} parameters that first two parameters specify that when we want to find the start of
     * the line, in which direction we have to move.
     * The next two parameters specify that when we want to find
     * the end of the line, in which direction we have to move.
     *
     * @param coordinate     the base cell which we want to calculate the score based on that
     * @param startRowInc    the amount of row increment when we want to move toward the start of the line
     * @param startColumnInc the amount of column increment when we want to move toward the start of the line
     * @param endRowInc      the amount of row increment when we want to move toward the end of the line
     * @param endColumnInc   the amount of column increment when we want to move toward the end of the line
     * @return a number equals or greater than 0 that indicates the score of the given coordinate
     * using the given direction.
     */
    private int calculateScore(final Coordinate coordinate, final int startRowInc, final int startColumnInc, final int endRowInc, final int endColumnInc) {
        int startRow = coordinate.row;
        int startColumn = coordinate.column;
        int endRow = coordinate.row;
        int endColumn = coordinate.column;

        final Optional<Character> cellValue = getCell(coordinate.row, coordinate.column);

        if (cellValue.isPresent()) {
            // Finding the start of the line
            while (isInRange(startRow, startColumn)) {
                if (cellValue.equals(getCell(startRow + startRowInc, startColumn + startColumnInc))) {
                    startRow += startRowInc;
                    startColumn += startColumnInc;
                } else break;
            }

            // Finding the end of the line
            while (isInRange(endRow, endColumn)) {
                if (cellValue.equals(getCell(endRow + endRowInc, endColumn + endColumnInc))) {
                    endRow += endRowInc;
                    endColumn += endColumnInc;
                } else break;
            }

            return Math.max(endRow - startRow + 1, endColumn - startColumn + 1);
        }

        return 0;
    }
}
