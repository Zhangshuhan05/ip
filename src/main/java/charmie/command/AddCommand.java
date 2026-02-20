package charmie.command;

import java.io.IOException;

import charmie.storage.Storage;
import charmie.task.Task;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Represents a command that adds a new {@link Task} to the {@link TaskList}.
 *
 * <p>The {@code AddCommand} encapsulates the logic required to:
 * <ul>
 *     <li>Add a task to the in-memory task list.</li>
 *     <li>Persist the updated task list to storage.</li>
 *     <li>Return a confirmation message to the user.</li>
 * </ul>
 *
 * <p>This command does not terminate the application.
 */
public class AddCommand extends Command {

    /** The task to be added to the task list. */
    private final Task task;

    /**
     * Constructs an {@code AddCommand} with the specified task.
     *
     * @param task the {@code Task} to be added; must not be {@code null}
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Indicates whether this command should terminate the application.
     *
     * @return {@code false}, since adding a task does not exit the program
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the add operation.
     *
     * <p>The task is first added to the given {@code TaskList}. The updated
     * list is then saved using the provided {@code Storage}. If an
     * {@link IOException} occurs during saving, the exception is displayed
     * through the {@code Ui}.
     *
     * @param tasks   the {@code TaskList} to which the task will be added
     * @param ui      the {@code Ui} used to generate user-facing messages
     * @param storage the {@code Storage} responsible for persisting tasks
     * @return a confirmation message indicating the task was added,
     *         including the updated task count
     */
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