package charmie.command;

import charmie.storage.Storage;
import charmie.task.Task;
import charmie.task.TaskList;
import charmie.ui.Ui;
import javafx.util.Pair;

/**
 * Represents a command to update a specific task in the task list.
 * <p>
 * The UpdateCommand modifies a task's field (such as description, deadline, or start/end
 * times) and saves the updated task list to storage.
 */
public class UpdateCommand extends Command {

    /** Index of the task to update in the task list. */
    private int index;

    /** The new value to set for the task field. */
    private String updateValue;

    /** The field of the task to update (e.g., "name", "by", "from", "to"). */
    private String updateField;

    /**
     * Constructs an UpdateCommand with the task index and update details.
     *
     * @param index the index of the task to update
     * @param updateDetails a Pair containing the field to update (key) and the new value (value)
     */
    public UpdateCommand(int index, Pair<String, String> updateDetails) {
        this.index = index;
        this.updateField = updateDetails.getKey();
        this.updateValue = updateDetails.getValue();
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return false since UpdateCommand does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the update command.
     * <p>
     * Updates the specified task's field with the new value, saves the updated task
     * list to storage, and returns a message describing the update.
     *
     * @param tasks the TaskList containing all tasks
     * @param ui the Ui instance to format messages
     * @param storage the Storage instance to save tasks
     * @return a string message describing the updated task, or an exception message if an error occurs
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) {
        Task oldTask = tasks.getTask(index);

        try {
            tasks.updateTask(index, updateField, updateValue);
            storage.saveToFile(tasks);
        } catch (Exception e) {
            return ui.showException(e);
        }

        return ui.updateTaskMsg(oldTask, tasks.getTask(index));
    }
}
