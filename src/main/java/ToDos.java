public class ToDos extends Task {

    ToDos(String description) {
        super(description);
    }

    @Override
    public String getString() {
        return "[T]" + "[" + getStatusIcon() + "] " + description;
    }
}
