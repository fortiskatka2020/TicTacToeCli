package com.tictactoecli.tictactoecli.services;

import com.tictactoecli.tictactoecli.exceptions.NonExistingField;
import com.tictactoecli.tictactoecli.exceptions.OccupiedFieldException;
import com.tictactoecli.tictactoecli.exceptions.OngoingGameException;
import com.tictactoecli.tictactoecli.model.Board;
import com.tictactoecli.tictactoecli.model.Symbol;
import com.tictactoecli.tictactoecli.model.GameState;
import org.springframework.stereotype.Service;

@Service
public class GameRunnerImpl implements GameRunner{

    Board board; // A variable that holds the game board.
    Symbol currentSymbol; // A variable to keep track of the current symbol to be played.
    GameState state; // Keeps track of the current game state, draw, playing, X win or O win.

    public GameRunnerImpl() {
        board = new Board();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Symbol getCurrentSymbol() {
        return currentSymbol;
    }

    @Override
    public GameState getState() {
        return state;
    }

    /**
     * This function parse a user command and apply the appropriate actions.
     *  @param command The next user command that the application has to parse.
     * @throws OccupiedFieldException This function takes and rethrows the earlier generated messages.
     * @throws NonExistingField This function takes and rethrows the earlier generated messages.
     * @throws OngoingGameException This function takes and rethrows the earlier generated messages.
     */
    @Override
    public void parseCommand(String command) throws OccupiedFieldException, NonExistingField, OngoingGameException {
        if(command.equalsIgnoreCase("new game")){
            initGame();
        } else {
            if(state == GameState.PLAYING) {
                addSymbol(command);
            } else {
                System.out.println("Start a new game to play. The current game is over.");
            }
        }
        if(state == GameState.PLAYING) {
            askCurrentPlayerToPlay();
        }
        board.printState();
    }


    /**
     * This function will validate the command and tell the board to add the new symbol to given row and column.
     * @param command A command that will be validated and should contain the row and column of the field to be added.
     * @throws OccupiedFieldException Throws the occupiedFieldException further up if the desired field already has a symbol.
     * @throws NonExistingField Throws the NonExistingField exception if the user tries to write to a field outside the grid.
     */
    private void addSymbol(String command) throws OccupiedFieldException, NonExistingField {
        Integer[] coordinates = validateCoordinates(command);
        int row = coordinates[0];
        int column = coordinates[1];
        board.setSymbol(currentSymbol, row, column);
        if(board.hasWon(currentSymbol, row, column)) {
            state = currentSymbol == Symbol.X ? GameState.WIN_X : GameState.WIN_O;
            System.out.println("The game has ended and " + currentSymbol.toString() + " has won.");
            System.out.println("Type \"New game\" to start a new game or \"Quit\" to exit.");
        } else if(board.isDraw()) {
            state = GameState.DRAW;
            System.out.println("The game has ended in a draw.");
            System.out.println("Type \"New game\" to start a new game or \"Quit\" to exit.");
        }
        currentSymbol = currentSymbol == Symbol.X ? Symbol.O : Symbol.X;
    }

    /**
     * This function splits the user input by space.
     * It verifies that the user has provided two different arguments and ensures these arguments can be
     * converted to integers. Otherwise it will throw Illegal argument exceptions.
     * @param command The user input
     * @return A two dimensional array containing the row and column
     */
    private Integer[] validateCoordinates(String command) {
        String[] coordinates = command.split(" ");
        if(coordinates.length != 2) {
            throw new IllegalArgumentException("Please ensure to only submit two coordinates");
        }
        try {
            int row = Integer.parseInt(coordinates[0])-1;
            int column = Integer.parseInt(coordinates[1])-1;
            return new Integer[]{row,column};
        } catch( NumberFormatException e) {
            throw new IllegalArgumentException("The coordinates must be two numbers");
        }
    }

    /**
     * This function will initialize the game board to a new state.
     * It will set the current turn to value X.
     * It will change the GameState to playing.
     */
    public void initGame() throws OngoingGameException {
        if(state != GameState.PLAYING) {
            board.initializeNewBoard();
            currentSymbol = Symbol.X;
            state = GameState.PLAYING;
        } else {
            throw new OngoingGameException("Finish the current game before starting a new one");
        }
    }

    /**
     * A method that prints out a request for the current player to make a move.
     */
    public void askCurrentPlayerToPlay() {
        System.out.println("Player '" + currentSymbol.toString() + "' has to make a move.");
        System.out.println("Play in a field by typing the row and column separated by a space.");
    }

}