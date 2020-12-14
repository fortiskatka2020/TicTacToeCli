package com.tictactoecli.tictactoecli.model;

public enum Symbol {

    X ("X"),
    O ("O"),
    BLANK ("-");

    private final String name;

    private Symbol(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
