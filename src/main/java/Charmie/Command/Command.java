package Charmie.Command;


import Charmie.Exception.CharmieException;
import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.TaskList;

/**
 * Abstract class Command represents a user command.
 */
public abstract class Command {
    protected boolean isExit = false;

    abstract public void run(TaskList Tasks, Ui ui, Storage storage) throws CharmieException;

    public boolean isExit() {

        return isExit;
    }

}
