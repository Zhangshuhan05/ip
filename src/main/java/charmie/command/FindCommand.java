package charmie.command;

import java.util.List;

import charmie.exception.CharmieException;
import charmie.storage.Storage;
import charmie.task.Task;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Command to find tasks matching a keyword.
 *
 * FindCommand searches the task list for tasks containing the specified keyword
 * and displays the matching results to the user.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified search keyword.
     *
     * @param keyword the search keyword to find matching tasks
     */
    public FindCommand(String keyword) {

        this.keyword = keyword;
    }

    @Override
    public boolean isExit() {

        return false;
    }

    @Override
    public String run(TaskList tasks, Ui ui, Storage storage) throws CharmieException {
        if (keyword.isEmpty()) {
            throw new CharmieException("OOPS!!! The search keyword cannot be empty :(");
        }
        List<Task> matches = tasks.findTasks(keyword);
        return ui.findTasksMsg(matches, keyword);
    }
}
