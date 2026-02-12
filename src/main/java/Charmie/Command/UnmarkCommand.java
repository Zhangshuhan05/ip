package Charmie.Command;

import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.TaskList;

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
