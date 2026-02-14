package charmie.exception;

/**
 * Custom exception class for Charmie application errors.
 *
 * CharmieException is thrown when an error occurs during task management operations
 * or command execution within the Charmie application.
 */
public class CharmieException extends Exception {

    /**
     * Constructs a CharmieException with the specified error message.
     *
     * @param message the detail message explaining the error
     */
    public CharmieException(String message) {
        super(message);
    }

}
