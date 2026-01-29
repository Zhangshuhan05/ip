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
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 3) continue;
                String type = parts[0];
                boolean done = parts[1].equals("1");
                String desc = parts[2];

                switch (type) {
                    case "T":
                        ToDo todo = new ToDo(desc);
                        if (done) todo.markAsDone();
                        list.add(todo);
                        break;
                    case "D":
                        if (parts.length < 4) continue;
                        Deadline d = new Deadline(desc, parts[3]);
                        if (done) d.markAsDone();
                        list.add(d);
                        break;
                    case "E":
                        if (parts.length < 4) continue;
                        String[] times = parts[3].split(",", 2);
                        Event e = new Event(desc, times[0], times.length > 1 ? times[1] : "");
                        if (done) e.markAsDone();
                        list.add(e);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}