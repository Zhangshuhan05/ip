package Charmie.Command;

import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.TaskList;

/**
 * Represents a command that exits the application.
 * <p>
 * When executed, this command displays a goodbye message to the user
 * and signals that the program should terminate.
 */
public class ExitCommand extends Command {

    /**
     * Indicates that this command triggers program termination.
     *
     * @return true, since executing this command exits the program
     */
    @Override
    public boolean isExit() {

        return true;
    }

    /**
     * Executes the exit command.
     * <p>
     * Displays a goodbye message to the user through the UI.
     *
     * @param tasks The current task list (not used in this command)
     * @param ui The user interface used to display messages
     * @param storage The storage handler (not used in this command)
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.goodbyeMsg();
    }
}
