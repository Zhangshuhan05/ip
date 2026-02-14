package charmie.command;

import java.io.IOException;

import charmie.storage.Storage;
import charmie.task.Task;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 * <p>
 * This command removes the task at the specified index from the given TaskList,
 * displays a message to the user using the Ui, and saves the updated task list
 * to storage. It does not cause the application to exit.
 * </p>
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a new DeleteCommand for the specified task index.
     *
     * @param index The 0-based index of the task to delete.
     */
    public DeleteCommand(int index) {

        this.index = index;
    }

    /**
     * Executes the delete command.
     * <p>
     * Removes the task at the specified index from the TaskList, displays a
     * confirmation message to the user, and saves the updated task list
     * to the provided Storage. If an IOException occurs during saving, it
     * is displayed to the user via the Ui.
     * </p>
     *
     * @param tasks   The TaskList from which the task will be removed.
     * @param ui      The Ui instance used to display messages to the user.
     * @param storage The Storage instance used to save the updated task list.
     */
    public String run(TaskList tasks, Ui ui, Storage storage) {
        Task taskToDelete = tasks.getTask(index);
        tasks.removeTask(index);

        try {
            storage.saveToFile(tasks);
        } catch (IOException e) {
            return ui.showException(e);
        }

        return ui.delTaskMsg(taskToDelete, tasks.getSize());
    }

    /**
     * Indicates whether this command will exit the application.
     *
     * @return false, because deleting a task does not exit the application.
     */
    @Override
    public boolean isExit() {

        return false;
    }
}
