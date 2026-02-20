package charmie.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks for the Charmie application.
 * <p>
 * Provides methods to add, remove, mark/unmark, search, and update tasks.
 */
public class TaskList {

    /** The internal list of tasks. */
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
     * @param task the Task object to be added
     */
    public void addTask(Task task) {
        this.list.add(task);
    }

    /**
     * Removes the task at the specified index from the task list.
     *
     * @param index the index of the task to remove
     */
    public void removeTask(int index) {
        this.list.remove(index);
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index the index of the task to mark as done
     * @return the Task object that was marked as done
     */
    public Task markTask(int index) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("Task index out of bounds");
        }
        Task task = getTask(index);
        task.markAsDone();
        return task;
    }

    /**
     * Unmarks the task at the specified index as not done.
     *
     * @param index the index of the task to unmark
     * @return the Task object that was unmarked
     */
    public Task unmarkTask(int index) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("Task index out of bounds");
        }
        Task task = getTask(index);
        task.unMark();
        return task;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int getSize() {
        return this.list.size();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index the index of the task to retrieve
     * @return the Task object at the given index
     */
    public Task getTask(int index) {
        return this.list.get(index);
    }

    /**
     * Finds all tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword the keyword to search for
     * @return a list of matching Task objects
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

    /**
     * Updates a task at the specified index by creating a new task with the updated field.
     *
     * @param index the index of the task to update
     * @param field the field to update (e.g., "name", "by", "from", "to")
     * @param newValue the new value for the field
     */
    public void updateTask(int index, String field, String newValue) {
        Task oldTask = this.getTask(index);
        Task newTask = oldTask.update(field, newValue);
        list.set(index, newTask);
    }
}
