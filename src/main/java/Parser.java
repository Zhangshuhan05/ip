public class Parser {

    public static String getInstruction(String input) {
        String[] split = input.trim().split(" ", 2);
        return split[0];
    }

    public static String getDetails(String input) {
        String[] split = input.trim().split(" ", 2);
        return split.length > 1 ? split[1] : "";
    }

    public static Task parseTask(String instruction, String details)
            throws CharmieException {

        switch (instruction) {
            case "todo":
                if (details.isEmpty()) {
                    throw new CharmieException("OOPS!!! The description of a todo cannot be empty :(");
                }
                return new ToDo(details);

            case "deadline":
                String[] d = details.split("/by", 2);
                if (d.length < 2 || d[0].trim().isEmpty() || d[1].trim().isEmpty()) {
                    throw new CharmieException("OOPS!!! I need more details for your deadline :(");
                }
                return new Deadline(d[0].trim(), d[1].trim());

            case "event":
                String[] e = details.split("/", 3);
                if (e.length < 3) {
                    throw new CharmieException("OOPS!!! I need more details for your event :(");
                }
                String desc = e[0].trim();
                String from = e[1].replace("from", "").trim();
                String to = e[2].replace("to", "").trim();
                return new Event(desc, from, to);

            default:
                return null; // non-task commands
        }
    }
}

