import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class TaskList {
    private List<Task> list;

    TaskList() {
        this.list = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.list.add(task);
    }

    public Task markTask(int index) {
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    public Task unmarkTask(int index) {
        Task task = getTask(index);
        task.unMark();
        return task;
    }

    public void removeTask(int index) {
        this.list.remove(index);
    }

    public int getSize() {
        return this.list.size();
    }

    public Task getTask(int index) {
        return this.list.get(index);
    }

    public void saveToDatabase() {
        File file = new File("./data/charmie.txt");

        try {
            file.getParentFile().mkdirs();

            FileWriter fw = new FileWriter(file, false); // overwrite
            for (Task task : list) {
                fw.write(task.saveToTaskList() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File("./data/charmie.txt");
        if (!file.exists()) {
            System.out.println("File not found at startup, starting empty list.");
            return;
        }

        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}