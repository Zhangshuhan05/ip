package charmie.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import charmie.parser.Parser;
import charmie.task.Task;
import charmie.task.TaskList;

/**
 * Handles file storage and retrieval of tasks for the Charmie application.
 *
 * Storage manages reading from and writing to a file to persist tasks between sessions.
 * It converts TaskList objects to file format and loads tasks from stored files.
 */
public class Storage {
    private final File file;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath the path to the file where tasks will be stored and loaded
     */
    public Storage(String filePath) {

        this.file = new File(filePath);
    }

    /**
     * Saves all tasks from the TaskList to the storage file.
     *
     * Creates parent directories if they don't exist, then writes each task
     * in file format to the file. Overwrites any existing file content.
     *
     * @param tasks the TaskList containing tasks to save
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void saveToFile(TaskList tasks) throws IOException {
        file.getParentFile().mkdirs();

        try (FileWriter fw = new FileWriter(file, false)) {
            for (int i = 0; i < tasks.getSize(); i++) {
                fw.write(tasks.getTask(i).saveToTaskList());
                fw.write(System.lineSeparator());
            }
        }
    }

    /**
     * Loads all tasks from the storage file into a TaskList.
     *
     * Reads the storage file line by line and parses each line into a Task object.
     * Returns an empty TaskList if the file does not exist yet.
     *
     * @return a TaskList containing all tasks loaded from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public TaskList loadFromFile() throws IOException {
        TaskList tasks = new TaskList();

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner s = new Scanner(file)) {
            loadTasks(tasks, s);
        }

        return tasks;
    }


    private void loadTasks(TaskList tasks, Scanner s) {
        while (s.hasNextLine()) {
            addTaskIfValid(tasks, s.nextLine());
        }
    }

    private void addTaskIfValid(TaskList tasks, String line) {
        Task task = Parser.parseTaskFromFile(line);
        if (task != null) {
            tasks.addTask(task);
        }
    }


}

