package charmie.command;

import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Represents a command to mark a task as not done (incomplete) in the task list.
 * <p>
 * The UnmarkCommand updates the task's completion status and persists the change
 * to storage.
 */
public class UnmarkCommand extends Command {

    /** The index of the task to unmark in the task list. */
    private final int index;

    /**
     * Constructs an UnmarkCommand for a specific task index.
     *
     * @param index the index of the task to unmark
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the unmark command.
     * <p>
     * Sets the task at the specified index as not done and saves the updated task
     * list to storage.
     *
     * @param tasks the TaskList containing all tasks
     * @param ui the Ui instance to format messages
     * @param storage the Storage instance to save tasks
     * @return a message confirming the task has been unmarked, or an exception message if an error occurs
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) {
        tasks.unmarkTask(index);

        try {
            storage.saveToFile(tasks);
        } catch (java.io.IOException e) {
            return ui.showException(e);
        }

        return ui.unmarkTaskMsg(tasks.getTask(index));
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return false since UnmarkCommand does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
