package Charmie.Storage;

import Charmie.Parser.Parser;
import Charmie.task.Task;
import Charmie.task.TaskList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public void saveToFile(TaskList tasks) throws IOException {
        file.getParentFile().mkdirs();

        try (FileWriter fw = new FileWriter(file, false)) {
            for (int i = 0; i < tasks.getSize(); i++) {
                fw.write(tasks.getTask(i).saveToTaskList());
                fw.write(System.lineSeparator());
            }
        }
    }

    public TaskList loadFromFile() throws IOException {
        TaskList tasks = new TaskList();

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                Task task = Parser.parseTaskFromFile(line);
                if (task != null) {
                    tasks.addTask(task);
                }
            }
        }
        return tasks;
    }
}

