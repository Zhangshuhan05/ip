package charmie.task;

/**
 * Abstract base class representing a general task in Charmie.
 * <p>
 * Each task has a description and a completion status. Subclasses
 * must implement methods to save the task to storage and update its fields.
 */
public abstract class Task {

    /** The description of the task. */
    protected String description;

    /** Indicates whether the task is done. */
    protected boolean isDone;

    /**
     * Constructs a Task with an empty description and not done status.
     */
    public Task() {
        this.description = " ";
        this.isDone = false;
    }

    /**
     * Constructs a Task with the given description and not done status.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if done, otherwise a space " "
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done (unmark).
     */
    public void unMark() {
        isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Returns the string representation of this task for display.
     *
     * @return a string containing the status icon and description
     */
    public String getString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts this task into a string suitable for saving to a file.
     *
     * @return a string representing this task for storage
     */
    public abstract String saveToTaskList();

    /**
     * Creates a new task with the specified field updated to a new value.
     *
     * @param field the field to update (e.g., "name", "by", "from", "to")
     * @param newValue the new value for the field
     * @return a new Task object with the updated field
     */
    public abstract Task update(String field, String newValue);
}
