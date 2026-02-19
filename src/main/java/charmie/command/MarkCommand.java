package charmie.command;

import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Represents a command to mark a task as done (completed) in the task list.
 * <p>
 * The MarkCommand updates the task's completion status and persists the change
 * to storage.
 */
public class MarkCommand extends Command {

    /** The index of the task to mark as done in the task list. */
    private final int index;

    /**
     * Constructs a MarkCommand for a specific task index.
     *
     * @param index the index of the task to mark as done
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the mark command.
     * <p>
     * Sets the task at the specified index as done and saves the updated task
     * list to storage.
     *
     * @param tasks the TaskList containing all tasks
     * @param ui the Ui instance to format messages
     * @param storage the Storage instance to save tasks
     * @return a message confirming the task has been marked done, or an exception message if an error occurs
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) {
        tasks.markTask(index);

        try {
            storage.saveToFile(tasks);
        } catch (java.io.IOException e) {
            return ui.showException(e);
        }

        return ui.markTaskMsg(tasks.getTask(index));
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return false since MarkCommand does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
