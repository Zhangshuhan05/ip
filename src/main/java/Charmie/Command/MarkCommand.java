package Charmie.Command;

import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.TaskList;

public class MarkCommand extends Command{
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        tasks.markTask(index);
        try {
            storage.saveToFile(tasks);
        } catch (java.io.IOException e) {
            ui.showException(e);
        }
        ui.markTaskMsg(tasks.getTask(index));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
