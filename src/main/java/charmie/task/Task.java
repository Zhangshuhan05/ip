package charmie.task;

/**
 * Abstract class Task represents a general task with a description and completion status
 * */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with an empty description and not done status.
     */
    public Task() {
        this.description = " ";
        this.isDone = false;
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {

        return (isDone ? "X" : " ");
    }

    public void markAsDone() {

        isDone = true;
    }

    public void unMark() {

        isDone = false;
    }

    public String getDescription() {

        return description;
    }

    public String getString() {

        return "[" + getStatusIcon() + "] " + description;
    }

    public abstract String saveToTaskList();

}
