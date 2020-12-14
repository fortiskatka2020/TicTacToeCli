package com.tictactoecli.tictactoecli.components;

import com.tictactoecli.tictactoecli.services.GameRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Scanner;

/**
 * A component that implements the command line runner.
 * This class will be automatically started and will listen to user input.
 * It has an autowired instance of GameRunner to which it passes the commands in case a user does not want to quit.
 */
@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private final GameRunner gameRunner; // Spring will provide this GameRunner since it is autowired.

    private boolean running = true; // This variable keeps track of the running state of the application.
    private final Scanner scanner = new Scanner(System.in); // The scanner used to receive the next user input.

    public MyRunner(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }


    /**
     * The run method that takes the user input from the CLI.
     * If the user input is quit it ends the method which will shut down the spring boot application.
     * If the user input is not quit it passes the command on to the GameRunner.
     */
    @Override
    public void run(String... args) {
        System.out.println("You are in Tic Tac Toe.");
        System.out.println("Type \"Quit\" to leave the game.");
        System.out.println("Type \"New game\" to start a new game.");
        do {
            String command = scanner.nextLine();
            if(command.equalsIgnoreCase("quit")){
                running = false;
            } else {
                try{
                    gameRunner.parseCommand(command);
                } catch (Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            }
        }while (running);
    }
}
