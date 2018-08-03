package com.metronom.tictactoe.controller;

import java.io.StringReader;

class ConfigTestParameterModel {
    StringReader reader;

    int boardLength;

    char player1Symbol;
    char player2Symbol;
    char computerSymbol;

    ConfigTestParameterModel() {
    }

    ConfigTestParameterModel(String input) {
        this.reader = new StringReader(input);
    }

    ConfigTestParameterModel(String input, int boardLength, char player1Symbol, char player2Symbol, char computerSymbol) {
        this.reader = new StringReader(input);
        this.boardLength = boardLength;
        this.player1Symbol = player1Symbol;
        this.player2Symbol = player2Symbol;
        this.computerSymbol = computerSymbol;
    }
}
