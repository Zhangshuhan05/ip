package charmie.command;

import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Command to mark a task as not done.
 *
 * UnmarkCommand marks a task at the specified index as incomplete in the task list
 * and persists the change to storage.
 */
public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

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

    @Override
    public boolean isExit() {

        return false;
    }
}
