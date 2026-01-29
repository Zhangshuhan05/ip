public class Deadline extends Task {
    protected String by;

    Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getString() {
        return "[D]" + "[" + getStatusIcon() + "] " + description + " (by: " + by + ")";
    }

    @Override
    public String saveToTaskList() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
