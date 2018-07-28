package com.metronom.tictactoe.conf;

import java.io.StringReader;

class ConfigTestParameterModel {
    StringReader reader;
    String errorMessage;

    int boardLength;

    char player1Symbol;
    char player2Symbol;
    char computerSymbol;

    ConfigTestParameterModel(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    ConfigTestParameterModel(String input, String errorMessage) {
        this.reader = new StringReader(input);
        this.errorMessage = errorMessage;
    }

    ConfigTestParameterModel(String input, int boardLength, char player1Symbol, char player2Symbol, char computerSymbol) {
        this.reader = new StringReader(input);
        this.boardLength = boardLength;
        this.player1Symbol = player1Symbol;
        this.player2Symbol = player2Symbol;
        this.computerSymbol = computerSymbol;
    }
}
