package edu.handong.csee.java.hw5.exceptions;

/**
 * This class returns an error message if the user typed in an invalid command
 */
public class InvalidCommandException extends Exception{
    /**
     * Constructs a new InvalidCommandException with the specified error message
     */
    public InvalidCommandException(String message) {
        super(message);
    }

}
