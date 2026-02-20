package charmie.command;

import charmie.exception.CharmieException;
import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Represents an abstract user command in the Charmie application.
 *
 * <p>Each concrete subclass of {@code Command} defines a specific action
 * (e.g., add, delete, list) that can be executed by the user.
 * A command operates on the {@link TaskList}, may interact with
 * {@link Storage}, and produces output through the {@link Ui}.
 *
 * <p>Subclasses must implement the {@link #run(TaskList, Ui, Storage)}
 * method to define their execution logic.
 */
public abstract class Command {

    /**
     * Indicates whether this command should terminate the application.
     * Defaults to {@code false}. Subclasses may modify this if needed.
     */
    protected boolean isExit = false;

    /**
     * Executes the command.
     *
     * <p>This method contains the core logic of the command. It may:
     * <ul>
     *     <li>Modify the {@code TaskList}</li>
     *     <li>Interact with {@code Storage} for persistence</li>
     *     <li>Generate output messages via {@code Ui}</li>
     * </ul>
     *
     * @param tasks   the {@code TaskList} containing all current tasks
     * @param ui      the {@code Ui} used to generate user-facing messages
     * @param storage the {@code Storage} used for reading or saving data
     * @return a formatted {@code String} representing the result of the command execution
     * @throws CharmieException if an error occurs during command execution
     */
    public abstract String run(TaskList tasks, Ui ui, Storage storage)
        throws CharmieException;

    /**
     * Returns whether this command signals the application to exit.
     *
     * @return {@code true} if the application should terminate after execution,
     *         {@code false} otherwise
     */
    public boolean isExit() {
        return isExit;
    }
}