package Charmie.Command;

import java.io.IOException;
import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.Task;
import Charmie.task.TaskList;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {

        this.task = task;
    }

    @Override
    public boolean isExit() {

        return false;
    }

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
