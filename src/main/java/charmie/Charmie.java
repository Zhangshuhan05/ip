package charmie;

import java.io.IOException;

import charmie.command.Command;
import charmie.exception.CharmieException;
import charmie.parser.Parser;
import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.ui.Ui;

/**
 * Main entry point for the chatbot Charmie.
 *
 * Charmie is a chatbot that helps users manage their tasks through a command-line interface.
 * It handles loading tasks from storage, processing user commands, and persisting changes.
 *
 * The application initializes with a storage file path and manages three core components:
 * - UI: Handles user interaction and message display
 * - Storage: Manages file I/O for task persistence
 * - TaskList: Stores and manages the user's tasks
 */
public class Charmie {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs a new Charmie chatbot instance.
     *
     * Initializes the UI, Storage, and TaskList components. Attempts to load existing tasks
     * from the specified storage file. If loading fails (e.g., file not found), starts with an empty task list.
     *
     * @param filePath the path to the storage file for saving and loading tasks
     */
    public Charmie(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = loadTasks();
    }

    /**
     * Loads tasks from storage file.
     *
     * Attempts to load tasks from the storage file. If an IOException occurs
     * (file not found or read error), returns an empty TaskList.
     *
     * @return TaskList containing loaded tasks, or an empty TaskList if loading fails
     */
    private TaskList loadTasks() {
        try {
            return storage.loadFromFile();
        } catch (IOException e) {
            return new TaskList();
        }
    }

    /**
     * Runs the main chatbot loop.
     *
     * Displays a welcome message and continuously reads user commands until
     * an exit command is received. Each command is parsed, executed, and the
     * response is displayed to the user. CharmieExceptions are caught and
     * displayed as error messages.
     */
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

    /**
     * Processes a single user command and returns the response.
     *
     * Parses the input string into a command and executes it on the task list.
     * This method is typically used by GUI interfaces that don't use the main loop.
     *
     * @param input the user's command as a string
     * @return the response message from executing the command, or error message if an exception occurs
     */
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
