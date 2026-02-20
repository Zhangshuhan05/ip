package charmie.command;

import java.util.List;

import charmie.exception.CharmieException;
import charmie.storage.Storage;
import charmie.task.Task;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Represents a command that searches for tasks containing a given keyword.
 *
 * A {@code FindCommand} filters tasks in the {@link TaskList}
 * and returns the matching results to the user.
 */
public class FindCommand extends Command {

    /** The keyword used to search for matching tasks. */
    private final String keyword;

    /**
     * Constructs a {@code FindCommand} with the specified keyword.
     *
     * @param keyword the keyword used to search for tasks
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Indicates whether this command should terminate the application.
     *
     * @return {@code false}, since finding tasks does not exit the program
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the find operation.
     *
     * The task list is searched for tasks containing the given keyword.
     * If the keyword is empty, a {@code CharmieException} is thrown.
     *
     * @param tasks   the {@code TaskList} containing all current tasks
     * @param ui      the {@code Ui} used to generate user-facing messages
     * @param storage the {@code Storage} object (not used in this command)
     * @return a formatted {@code String} showing the matching tasks
     * @throws CharmieException if the keyword is empty
     */
    @Override
    public String run(TaskList tasks, Ui ui, Storage storage)
        throws CharmieException {

        if (keyword.isEmpty()) {
            throw new CharmieException(
                "OOPS!!! The search keyword cannot be empty :(");
        }

        List<Task> matches = tasks.findTasks(keyword);
        return ui.findTasksMsg(matches, keyword);
    }
}