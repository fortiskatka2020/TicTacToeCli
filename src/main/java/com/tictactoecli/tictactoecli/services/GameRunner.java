package com.tictactoecli.tictactoecli.services;


import com.tictactoecli.tictactoecli.model.Board;
import com.tictactoecli.tictactoecli.model.GameState;
import com.tictactoecli.tictactoecli.model.Symbol;

public interface GameRunner {

    Board getBoard();

    Symbol getCurrentSymbol();

    GameState getState();

    /**
     * A method that parses user commands to advance the game.
     * @param command The next user command that the application has to parse.
     * @throws Throwable parseCommand has a set of exceptions it can throw based on the input
     */
    void parseCommand(String command) throws Throwable;

}
