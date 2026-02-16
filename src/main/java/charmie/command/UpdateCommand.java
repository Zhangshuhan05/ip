package charmie.command;

import javafx.util.Pair;

import charmie.storage.Storage;
import charmie.task.Task;
import charmie.task.TaskList;
import charmie.ui.Ui;

public class UpdateCommand extends Command {
    private int index;
    private String updateValue;
    private String updateField;

    public UpdateCommand(int index, Pair<String, String> updateDetails) {
        this.index = index;
        this.updateField = updateDetails.getKey();
        this.updateValue = updateDetails.getValue();
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public String run (TaskList tasks, Ui ui, Storage storage) {
        Task oldTask = tasks.getTask(index);

        try {
            tasks.updateTask(index, updateField, updateValue);
            storage.saveToFile(tasks);
        } catch (Exception e) {
            return ui.showException(e);
        }

        return ui.updateTaskMsg(oldTask, tasks.getTask(index));
    }
}
