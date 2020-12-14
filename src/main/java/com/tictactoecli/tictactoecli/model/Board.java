package com.tictactoecli.tictactoecli.model;


import com.tictactoecli.tictactoecli.exceptions.NonExistingField;
import com.tictactoecli.tictactoecli.exceptions.OccupiedFieldException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Board {

    private static final int size = 3;
    private List<List<Symbol>> grid;

    public Board() {

    }

    public static int getSize() {
        return size;
    }

    public List<List<Symbol>> getGrid() {
        return grid;
    }

    /**
     * This function will verify that the row and column are in the grid.
     * After that the function adds the symbol to that coordinate if that field is empty or throws an exception.
     * @param playedSymbol The symbol to be added to the grid
     * @param row The row where the symbol should be added
     * @param column The column where the symbol should be added
     * @throws NonExistingField An exception thrown if the field does not exist in the grid.
     * @throws OccupiedFieldException An exception thrown if the field is already occupied by a value other than Blank.
     */
    public void setSymbol(Symbol playedSymbol, int row, int column) throws NonExistingField, OccupiedFieldException {
        if(row >= size || column >= size) {
            throw new NonExistingField("The field does not exist. Select a field within the range.");
        }
        if(grid.get(row).get(column) == Symbol.BLANK) {
            grid.get(row).set(column, playedSymbol);
        } else {
            throw new OccupiedFieldException("This field is already occupied. Please select an empty field.");
        }
    }

    /**
     * A function that initializes the new board to a grid full of blank symbols.
     */
    public void initializeNewBoard() {
        grid = new ArrayList<>();
        for(int rowIndex = 0; rowIndex < size; rowIndex++) {
            grid.add(new ArrayList<>());
            for(int columnIndex = 0; columnIndex < size; columnIndex++) {
                grid.get(rowIndex).add(Symbol.BLANK);
            }
        }
    }

    /**
     * A function that checks if a certain symbol has won.
     * @param value The most recently played symbol.
     * @param row The row the most recently played symbol was played in.
     * @param column The column the most recently played symbol was played in.
     * @return A boolean indicating if the most recently added symbol resulted in a win for that symbol.
     */
    public boolean hasWon(Symbol value, int row, int column) {
        boolean hasRow = this.finishedRow(value, row);
        boolean hasColumn = this.finishedColumn(value, column);
        boolean hasDiagonal = this.finishedDiagonal(value);

        return hasColumn || hasRow || hasDiagonal;
    }

    /**
     * A function that verifies if the most recently played symbol completed a full diagonal for that symbol.
     * @param value The most recently played symbol.
     * @return A boolean indicating if the most recently played symbol completed a full diagonal for that symbol.
     */
    private boolean finishedDiagonal(Symbol value) {
        boolean diagonal = true;
        boolean reverseDiagonal = true;

        for(int index = 0; index < size; index++) {
            if(grid.get(index).get(index) != value) {
                diagonal = false;
            }
            if(grid.get(index).get(size-index -1) != value) {
                reverseDiagonal = false;
            }
        }

        return reverseDiagonal || diagonal;
    }

    /**
     * his function verifies wether all values in the most recently played row match the most recently played symbol.
     * @param value This is the most recently added symbol.
     * @param row This is the row in which the most recently played symbol was placed.
     * @return  A boolean indicating if the most recently played symbol completed a full row of that symbol.
     */
    private boolean finishedRow(Symbol value, int row) {
        return grid.get(row).stream()
                .allMatch(x -> x == value);
    }

    /**
     * This function verifies wether all values in the most recently played column match the most recently played symbol.
     * If that is the case this play resulted in a win.
     * @param value This is the most recently added symbol.
     * @param column This is the column in which the most recently played symbol was placed.
     * @return A boolean indicating if the most recently played symbol completed a full column of that symbol.
     */
    private boolean finishedColumn(Symbol value, int column){
        boolean result = IntStream.range(0, size).noneMatch(rowIndex -> grid.get(rowIndex).get(column) != value);
        return result;
    }

    /**
     * This function is only called after a check for a win is done.
     * If a play does not win the game and there are no empty fields left, the game is a draw.
     * This function veryfies that there are no empty fields left to play on.
     * @return A boolean indicating wether or not the board is a draw in the current state.
     */
    public boolean isDraw() {
        boolean noEmptyFields = grid.stream().flatMap(List::stream).noneMatch(x -> x == Symbol.BLANK);
        return noEmptyFields;
    }

    /**
     * A function that loops through the rows and prints out all the rows.
     * It will also help the user by providing seperators between columns and rows.
     * It will help the user by printing the row and column numbers on the side of the board.
     */
    public void printState() {
        StringBuilder builder = new StringBuilder();
        for(int index = 0; index < size; index++) {
            builder.append("   ");
            builder.append(index + 1);
        }
        System.out.println(builder.toString());
        printRow(0);
        for(int index = 1; index <size; index++) {
            System.out.println("  -----------");
            printRow(index);
        }
    }

    /**
     * This function prints the current state of a row to the console.
     * @param rowIndex The row that needs to be printed to the console
     */
    private void printRow(int rowIndex) {
        StringBuilder builder = new StringBuilder((rowIndex + 1) + "  " + grid.get(rowIndex).get(0).toString());
        for(int index = 1; index <size; index++) {
            builder.append(" | ").append(grid.get(rowIndex).get(index).toString());
        }
        System.out.println(builder.toString());
    }

    /**
     * A function that returns the symbol in a given field.
     * @param row The desired row.
     * @param column The desired column.
     * @return Symbol in that field. Either blank, X or O.
     */
    public Symbol getSymbol(int row, int column) {
        return this.grid.get(row).get(column);
    }


}
