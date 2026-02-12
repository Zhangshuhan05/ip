package Charmie.Command;

import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.TaskList;

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
