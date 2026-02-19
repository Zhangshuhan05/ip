package charmie.task;

/**
 * Represents a simple to-do task in Charmie.
 * A ToDo task only has a description and a completion status.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the given description.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of this ToDo task for display.
     * Includes the task type "[T]" and its completion status.
     *
     * @return a string representing this ToDo task
     */
    @Override
    public String getString() {
        return "[T]" + super.getString();
    }

    /**
     * Converts this ToDo task into a string suitable for saving to a file.
     * Format: T | [done status] | [description]
     *
     * @return a string representing this task for storage
     */
    @Override
    public String saveToTaskList() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Creates a new ToDo task with an updated field.
     * <p>
     * For ToDo tasks, only the description can be updated; the completion status is reset.
     *
     * @param field the field to update (ignored for ToDo)
     * @param newValue the new value for the field
     * @return a new ToDo task with the updated description
     */
    @Override
    public Task update(String field, String newValue) {
        return new ToDo(newValue);
    }
}
