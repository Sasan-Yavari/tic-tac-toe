package com.metronom.tictactoe.entity;

public class CellScore {
    int verticalScore;
    int horizontalScore;
    int diagonal1Score;
    int diagonal2Score;

    public boolean isGreaterThan(int value) {
        return horizontalScore >= value || verticalScore >= value || diagonal1Score >= value || diagonal2Score >= value;
    }
}
