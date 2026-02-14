package charmie.command;

import java.io.IOException;

import charmie.storage.Storage;
import charmie.task.Task;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Command to add a new task to the task list.
 *
 * AddCommand creates and stores a task, then saves it to persistent storage.
 * The task is added to the TaskList and a confirmation message is returned to the user.
 */
public class AddCommand extends Command { //
    private final Task task;

    /**
     * Constructs an AddCommand with the specified task.
     *
     * @param task the Task object to be added to the task list
     */
    public AddCommand(Task task) { //

        this.task = task;
    }

    @Override
    public boolean isExit() {

        return false;
    }

    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) {
        tasks.addTask(task);

        try {
            storage.saveToFile(tasks);
        } catch (IOException e) {
            ui.showException(e);
        }

        return ui.addTaskMsg(task, tasks.getSize());
    }



}
