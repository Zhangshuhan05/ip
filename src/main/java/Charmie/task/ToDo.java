package Charmie.task;
/**
 * Child class ToDo represents a simple to-do task
 * */
public class ToDo extends Task {

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
