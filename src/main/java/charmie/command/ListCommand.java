package charmie.command;

import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Command to display all tasks in the task list.
 *
 * ListCommand retrieves and displays all tasks stored in the TaskList
 * to the user through the UI.
 */
public class ListCommand extends Command {

    @Override
    public boolean isExit() {

        return false;
    }

    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) {

        return ui.listTasks(tasks);
    }
}
