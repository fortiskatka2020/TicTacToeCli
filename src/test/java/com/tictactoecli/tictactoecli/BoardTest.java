package com.tictactoecli.tictactoecli;

import com.tictactoecli.tictactoecli.exceptions.NonExistingField;
import com.tictactoecli.tictactoecli.exceptions.OccupiedFieldException;
import com.tictactoecli.tictactoecli.model.Board;
import com.tictactoecli.tictactoecli.model.Symbol;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    Board board = new Board();

    @Test
    public void initializeBoard() {
        board.initializeNewBoard();
        assertEquals(board.isDraw(), false);
        assertEquals(board.getSize(), 3);
        assertEquals(board.getGrid().size(), board.getSize());
        for(int index =0; index < board.getSize(); index++) {
            assertEquals(board.getGrid().get(index).size(), board.getSize());
        }
        assertTrue(board.getGrid().stream().flatMap(List::stream).allMatch(x -> x == Symbol.BLANK));
    }

    @Test
    public void testDoubleFillGridCell() {
        board.initializeNewBoard();
        assertDoesNotThrow(() -> board.setSymbol(Symbol.X, 0, 0));
        assertThrows(OccupiedFieldException.class, () -> board.setSymbol(Symbol.O, 0 , 0));
    }

    @Test
    public void testSettingValue() {
        initializeBoard();
        Random rn = new Random();
        int row =  rn.nextInt(board.getSize());
        int column =  rn.nextInt(board.getSize());
        assertEquals(board.getSymbol(row, column), Symbol.BLANK);
        assertDoesNotThrow(() -> board.setSymbol(Symbol.X, row, column));
        assertEquals(board.getSymbol(row, column), Symbol.X);
    }

    @Test
    public void ableToFillEntireGrid() {
        board.initializeNewBoard();
        int gridSize = board.getSize();
        for(int rowIndex = 0; rowIndex < gridSize -1; rowIndex++) {
            for(int columnIndex = 0; columnIndex < gridSize -1; columnIndex++) {
                try {
                    board.setSymbol(Symbol.X, rowIndex, columnIndex);
                } catch (Throwable throwable) {
                    fail("All indexes are within the gridsize and adding a field should not fail.");
                }
            }
        }
    }

    @Test
    public void testFillCellOutsideGrid() {
        board.initializeNewBoard();
        int gridSize = board.getSize();
        assertThrows(NonExistingField.class, () -> board.setSymbol(Symbol.X, gridSize, gridSize -1));
        assertThrows(NonExistingField.class, () -> board.setSymbol(Symbol.X, gridSize -1, gridSize));
    }

    @Test
    public void testRowWinCondition() {
        board.initializeNewBoard();
        for(int index = 0; index < board.getSize(); index++) {
            try {
                board.setSymbol(Symbol.O, 0, index);
            } catch (NonExistingField nonExistingField) {
                fail("The coordinates should be within the board.");
            } catch (OccupiedFieldException e) {
                fail("We started from an empty board and all fields should be usable.");
            }
        }
        assertTrue(board.hasWon(Symbol.O,0,0));
    }

    @Test
    public void testColumnWinCondition() {
        board.initializeNewBoard();
        for(int index = 0; index < board.getSize(); index++) {
            try {
                board.setSymbol(Symbol.O, index, 0);
            } catch (NonExistingField nonExistingField) {
                fail("The coordinates should be within the board.");
            } catch (OccupiedFieldException e) {
                fail("We started from an empty board and all fields should be usable.");
            }
        }
        assertTrue(board.hasWon(Symbol.O,0,0));
    }

    @Test
    public void testDiagonalWinCondition() {
        board.initializeNewBoard();
        for(int index = 0; index < board.getSize(); index++) {
            try {
                board.setSymbol(Symbol.O, index, index);
            } catch (NonExistingField nonExistingField) {
                fail("The coordinates should be within the board.");
            } catch (OccupiedFieldException e) {
                fail("We started from an empty board and all fields should be usable.");
            }
        }

        assertTrue(board.hasWon(Symbol.O,0,0));

    }

    @Test
    public void testDrawCondition() {
        // This function will only be called after no win is detected.
        // This function will say a board without blanks is a draw independent of whether X or O has a win or not.
        board.initializeNewBoard();
        for(int rowIndex = 0; rowIndex < board.getSize(); rowIndex++) {
            for(int columnIndex = 0; columnIndex < board.getSize(); columnIndex++) {
                assertFalse(board.isDraw());
                try {
                    board.setSymbol(Symbol.O, rowIndex, columnIndex);
                } catch (NonExistingField nonExistingField) {
                    fail("The coordinates should be within the board.");
                } catch (OccupiedFieldException e) {
                    fail("We started from an empty board and all fields should be usable.");
                }
            }
        }
        assertTrue(board.isDraw());
    }




}
