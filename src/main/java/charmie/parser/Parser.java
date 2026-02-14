package charmie.parser;

import charmie.command.AddCommand;
import charmie.command.Command;
import charmie.command.DeleteCommand;
import charmie.command.ExitCommand;
import charmie.command.FindCommand;
import charmie.command.ListCommand;
import charmie.command.MarkCommand;
import charmie.command.UnmarkCommand;
import charmie.exception.CharmieException;
import charmie.task.Deadline;
import charmie.task.Event;
import charmie.task.Task;
import charmie.task.ToDo;

/**
 * Parser for user input and file data in the Charmie application.
 *
 * Parser handles parsing of user commands, task details, and file format conversions.
 * It translates user input into Command objects and converts various task formats
 * into Task objects.
 */
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
            return Integer.parseInt(details) - 1;
        } catch (NumberFormatException e) {
            throw new CharmieException("Please give a valid task number.");
        }
    }

    /**
     * Parses user input and returns the corresponding Command object.
     *
     * Extracts the instruction and details from the input string, then creates
     * and returns the appropriate Command based on the instruction. Supports commands
     * like "bye", "list", "delete", "mark", "unmark", "find", and task creation commands.
     *
     * @param input the user input string to parse
     * @return the Command object corresponding to the parsed input
     * @throws CharmieException if the input is invalid or unrecognized
     */
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


    /**
     * Parses task details and returns the corresponding Task object.
     *
     * Converts task instruction and details into a Task object. Supports three task types:
     * "todo", "deadline", and "event". Returns null if the instruction does not match
     * any supported task type.
     *
     * @param instruction the task type ("todo", "deadline", or "event")
     * @param details the task details string containing description and other parameters
     * @return the Task object created from the parsed details, or null if instruction is invalid
     * @throws CharmieException if the task details are incomplete or improperly formatted
     */
    public static Task parseTask(String instruction, String details) throws CharmieException {
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

    /**
     * Parses a task from a file format string.
     *
     * Converts a pipe-delimited string (from storage file) into a Task object.
     * The format is: `type | done | description | [details]`, where type is "T" (todo),
     * "D" (deadline), or "E" (event). Returns null if the format is invalid or incomplete.
     *
     * @param line the file format string to parse
     * @return the Task object created from the file string, or null if the format is invalid
     */
    public static Task parseTaskFromFile(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {

            return null;
        }

        String type = parts[0];
        boolean done = parts[1].equals("1");
        String desc = parts[2];

        Task task = null;

        switch (type) {
        case "T":
            task = new ToDo(desc);
            break;

        case "D":
            if (parts.length < 4) {
                return null;
            }
            task = new Deadline(desc, parts[3]);
            break;

        case "E":
            if (parts.length < 4) {
                return null;
            }
            String[] times = parts[3].split(" ", 2);
            if (times.length < 2) {
                return null;
            }
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

