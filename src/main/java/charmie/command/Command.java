package charmie.command;


import charmie.exception.CharmieException;
import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Abstract class Command represents a user command.
 */
public abstract class Command {
    protected boolean isExit = false;

    /**
     * Executes the command based on user specification.
     *
     * @param tasks the TaskList containing all tasks
     * @param ui the UI object for displaying the result of the command execution
     * @param storage the Storage object (used by certain commands)
     * @return the formatted string corresponding to the command execution result to be displayed to the user
     * @throws CharmieException if an error occurs during command execution
     */
    public abstract String run(TaskList tasks, Ui ui, Storage storage) throws CharmieException;

    public boolean isExit() {

        return isExit;
    }

}
