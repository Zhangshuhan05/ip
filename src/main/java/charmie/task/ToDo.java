package charmie.task;

/**
 * Child class ToDo represents a simple to-do task
 * */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the given description.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {

        super(description);
    }

    @Override
    public String getString() {

        return "[T]" + super.getString();
    }

    @Override
    public String saveToTaskList() {

        return "T | " + (isDone ? "1" : "0") + " | " + description;
    };
}
