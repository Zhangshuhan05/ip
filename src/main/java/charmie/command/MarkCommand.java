package charmie.command;

import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Command to mark a task as done.
 *
 * MarkCommand marks a task at the specified index as completed in the task list
 * and persists the change to storage.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param index the index of the task to mark as done
     */
    public MarkCommand(int index) {

        this.index = index;
    }

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

    @Override
    public boolean isExit() {

        return false;
    }
}
