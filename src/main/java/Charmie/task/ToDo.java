package Charmie.task;

public class ToDo extends Task {

    ToDo(String description) {
        super(description);
    }

    @Override
    public String getString() {
        return "[T]" + "[" + getStatusIcon() + "] " + description;
    }

    @Override
    public String saveToTaskList() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    };
}
