package com.metronom.tictactoe.entity;

import com.metronom.tictactoe.exceptions.InvalidCoordinateException;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    private Board board = Board.getInstance();

    @Before
    public void setUp() throws Exception {
        board.init(3);
    }

    @Test
    public void init() {
        assertEquals(3, board.getBoardLength());
        assertEquals(9, board.getFreeRoomCount());
    }

    @Test
    public void put() throws InvalidCoordinateException {
        board.put('x', new Coordinate(0, 0));
        assertEquals(Optional.of('x'), board.getCell(0, 0));
        assertEquals(board.getBoardLength() * board.getBoardLength() - 1, board.getFreeRoomCount());
    }

    @Test(expected = InvalidCoordinateException.class)
    public void putInInvalidCoordinate() throws InvalidCoordinateException {
        board.put('x', new Coordinate(-1, 0));
    }

    @Test(expected = InvalidCoordinateException.class)
    public void putDuplicate() throws InvalidCoordinateException {
        board.put('x', new Coordinate(0, 0));
        board.put('o', new Coordinate(0, 0));
    }

    @Test
    public void getFreeRoomCount() {
        assertEquals(board.getBoardLength() * board.getBoardLength(), board.getFreeRoomCount());
    }

    @Test
    public void getBoardLength() {
        assertEquals(3, board.getBoardLength());
    }

    @Test
    public void calculateHorizontalScore() throws InvalidCoordinateException {
        assertEquals(0, board.calculateHorizontalScore(new Coordinate(0, 0)));
        board.put('x', new Coordinate(0, 0));
        assertEquals(1, board.calculateHorizontalScore(new Coordinate(0, 0)));
        board.put('x', new Coordinate(0, 1));
        assertEquals(2, board.calculateHorizontalScore(new Coordinate(0, 0)));
        board.put('x', new Coordinate(0, 2));
        assertEquals(3, board.calculateHorizontalScore(new Coordinate(0, 0)));
    }

    @Test
    public void calculateVerticalScore() throws InvalidCoordinateException {
        assertEquals(0, board.calculateVerticalScore(new Coordinate(0, 0)));
        board.put('x', new Coordinate(0, 0));
        assertEquals(1, board.calculateVerticalScore(new Coordinate(0, 0)));
        board.put('x', new Coordinate(1, 0));
        assertEquals(2, board.calculateVerticalScore(new Coordinate(0, 0)));
        board.put('x', new Coordinate(2, 0));
        assertEquals(3, board.calculateVerticalScore(new Coordinate(0, 0)));
    }

    @Test
    public void calculateDiagonal1Score() throws InvalidCoordinateException {
        assertEquals(0, board.calculateDiagonal1Score(new Coordinate(0, 0)));
        board.put('x', new Coordinate(0, 0));
        assertEquals(1, board.calculateDiagonal1Score(new Coordinate(0, 0)));
        board.put('x', new Coordinate(1, 1));
        assertEquals(2, board.calculateDiagonal1Score(new Coordinate(0, 0)));
        board.put('x', new Coordinate(2, 2));
        assertEquals(3, board.calculateDiagonal1Score(new Coordinate(0, 0)));
    }

    @Test
    public void calculateDiagonal2Score() throws InvalidCoordinateException {
        assertEquals(0, board.calculateDiagonal2Score(new Coordinate(0, 2)));
        board.put('x', new Coordinate(0, 2));
        assertEquals(1, board.calculateDiagonal2Score(new Coordinate(0, 2)));
        board.put('x', new Coordinate(1, 1));
        assertEquals(2, board.calculateDiagonal2Score(new Coordinate(0, 2)));
        board.put('x', new Coordinate(2, 0));
        assertEquals(3, board.calculateDiagonal2Score(new Coordinate(0, 2)));
    }

    @Test
    public void getCell() throws InvalidCoordinateException {
        assertFalse(board.getCell(0, 0).isPresent());
        board.put('x', new Coordinate(0, 0));
        assertTrue(board.getCell(0, 0).isPresent());
        assertEquals(Optional.of('x'), board.getCell(0, 0));
    }
}