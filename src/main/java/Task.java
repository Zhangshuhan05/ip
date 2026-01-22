public class Task {
    protected String description;
    protected boolean isDone;

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

    public String getString() {
        return "[" + getStatusIcon() + "] " + description;
    }

}
