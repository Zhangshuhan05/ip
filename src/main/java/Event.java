public class Event extends Task {
    protected String start;
    protected String end;

    Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String getString() {
        return "[E]" + "[" + getStatusIcon() + "] " + description + " (from: " + start + " to: " + end + ")";
    }

    @Override
    public String saveToTaskList() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start + " " + end;
    }
}
