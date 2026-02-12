package Charmie.Parser;

import Charmie.Exception.CharmieException;
import Charmie.task.Deadline;
import Charmie.task.Event;
import Charmie.task.Task;
import Charmie.task.ToDo;
import Charmie.Command.Command;
import Charmie.Command.AddCommand;
import Charmie.Command.DeleteCommand;
import Charmie.Command.ListCommand;
import Charmie.Command.MarkCommand;
import Charmie.Command.UnmarkCommand;
import Charmie.Command.ExitCommand;
import Charmie.Command.FindCommand;

public class Parser {

    public static String getInstruction(String input) {
        String[] split = input.trim().split(" ", 2);
        return split[0];
    }

    public static String getDetails(String input) {
        String[] split = input.trim().split(" ", 2);
        return split.length > 1 ? split[1] : "";
    }

    private static int parseIndex(String details) throws CharmieException {
        try {
            return Integer.parseInt(details);
        } catch (NumberFormatException e) {
            throw new CharmieException("Please give a valid task number.");
        }
    }


    public static Command parse(String input) throws CharmieException {

        String instruction = getInstruction(input);
        String details = getDetails(input);

        switch (instruction) {

        case "bye":
            return new ExitCommand();

        case "list":
            return new ListCommand();

        case "delete":
            return new DeleteCommand(parseIndex(details));

        case "mark":
            return new MarkCommand(parseIndex(details));

        case "unmark":
            return new UnmarkCommand(parseIndex(details));

        case "find":
            return new FindCommand(details);

        default:
            Task task = parseTask(instruction, details);

            if (task == null) {
                throw new CharmieException(
                        "OOPS!!! I don't know what that means :-("
                );
            }

            return new AddCommand(task);
        }
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
                return null;
        }
    }

    public static Task parseTaskFromFile(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];

        Task task = null;

        switch (type) {
            case "T":
                task = new ToDo(desc);
                break;
            case "D":
                if (parts.length < 4) return null;
                task = new Deadline(desc, parts[3]);
                break;
            case "E":
                if (parts.length < 4) return null;
                String[] times = parts[3].split(" ", 2);
                if (times.length < 2) return null;
                task = new Event(desc, times[0], times[1]);
                break;
            default:
                return null;
        }

        if (done) {
            task.markAsDone();
        }
        return task;
    }

}

