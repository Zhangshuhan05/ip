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
                String response = c.run(tasks, ui, storage);
                System.out.println(response);
                isExit = c.isExit();

            } catch (CharmieException e) {
                System.out.println(ui.showException(e));
            }
        }

    }

    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.run(tasks, ui, storage);
        } catch (CharmieException e) {
            return e.getMessage();
        }
    }

    public String getWelcomeMessage() {
        return ui.welcomeMsg();
    }

    public static void main(String[] args) {

        new Charmie("./data/charmie.txt").run();
    }
}
