package com.tictactoecli.tictactoecli;

import com.tictactoecli.tictactoecli.exceptions.OngoingGameException;
import com.tictactoecli.tictactoecli.services.GameRunner;
import com.tictactoecli.tictactoecli.services.GameRunnerImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameRunnerTest {

    GameRunner gameRunner = new GameRunnerImpl();

    @Test
    public void initializingTest() {
        assertEquals(gameRunner.getState(), null);
        assertEquals(gameRunner.getCurrentSymbol(), null);


        assertDoesNotThrow(()-> gameRunner.parseCommand("new game"));
        assertThrows(OngoingGameException.class, () -> gameRunner.parseCommand("new game"));
    }
}
