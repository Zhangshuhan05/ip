package charmie.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Class TaskList manages a list of tasks
 * */
public class TaskList {
    private List<Task> list;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {

        this.list = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     *
     * @param task the Task object to be added to the list
     */
    public void addTask(Task task) {

        this.list.add(task);
    }

    /**
     * Marks the task at the specified index as done, setting its completion status to done.
     *
     * @param index the index of the task to be marked as done
     * @return the Task object that was marked as done
     */
    public Task markTask(int index) {
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    /**
     * Unmarks the task at the specified index, setting its completion status to not done.
     *
     * @param index the index of the task to be unmarked
     * @return the Task object that was unmarked
     */
    public Task unmarkTask(int index) {
        Task task = getTask(index);
        task.unMark();
        return task;
    }

    /**
     * Removes the task at the specified index from the task list.
     *
     * @param index the index of the task to be removed
     */
    public void removeTask(int index) {

        this.list.remove(index);
    }

    public int getSize() {

        return this.list.size();
    }

    public Task getTask(int index) {

        return this.list.get(index);
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for in task descriptions
     * @return a list of tasks whose descriptions contain the keyword
     */
    public List<Task> findTasks(String keyword) {
        List<Task> matches = new ArrayList<>();
        for (Task task : list) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(task);
            }
        }
        return matches;
    }

}
