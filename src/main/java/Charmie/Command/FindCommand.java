package Charmie.Command;

import java.io.IOException;
import java.util.List;

import Charmie.Exception.CharmieException;
import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.Task;
import Charmie.task.TaskList;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {

        this.keyword = keyword;
    }

    @Override
    public boolean isExit() {

        return false;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws CharmieException{
        if (keyword.isEmpty()) {
            throw new CharmieException("OOPS!!! The search keyword cannot be empty :(");
        }
        List<Task> matches = tasks.findTasks(keyword);
        ui.findTasksMsg(matches, keyword);
    }

}
