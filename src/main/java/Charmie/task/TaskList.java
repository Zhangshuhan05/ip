package Charmie.task;

import java.util.List;
import java.util.ArrayList;

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
}