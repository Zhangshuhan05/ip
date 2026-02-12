package Charmie;

import Charmie.Command.Command;
import Charmie.Exception.CharmieException;
import Charmie.Parser.Parser;
import Charmie.Storage.Storage;
import Charmie.Ui.Ui;
import Charmie.task.TaskList;

import java.io.IOException;

public class Charmie {

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public Charmie(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = loadTasks();
    }

    private TaskList loadTasks() {
        try {
            return storage.loadFromFile();
        } catch (IOException e) {
            return new TaskList();
        }
    }

    public void run() {
        ui.welcomeMsg();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand().trim();

                Command c = Parser.parse(fullCommand);
                c.run(tasks, ui, storage);
                isExit = c.isExit();

            } catch (CharmieException e) {
                ui.showException(e);
            }
        }
    }

    public static void main(String[] args) {

        new Charmie("./data/charmie.txt").run();
    }
}
