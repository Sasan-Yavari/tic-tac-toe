package com.metronom.tictactoe.entity;

public class PlayerBuilder {
    private String name;
    private char symbol;
    private boolean aiSupport = false;

    public PlayerBuilder() {
    }

    public PlayerBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public PlayerBuilder withSymbol(final char symbol) {
        this.symbol = symbol;
        return this;
    }

    public PlayerBuilder withAISupport() {
        this.aiSupport = true;
        return this;
    }

    public Player build() {
        return new Player(name, symbol, aiSupport);
    }
}
