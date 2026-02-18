package charmie.parser;

import javafx.util.Pair;

import charmie.command.AddCommand;
import charmie.command.Command;
import charmie.command.DeleteCommand;
import charmie.command.ExitCommand;
import charmie.command.FindCommand;
import charmie.command.ListCommand;
import charmie.command.MarkCommand;
import charmie.command.UnmarkCommand;
import charmie.command.UpdateCommand;
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
        String details;

        if (split.length > 1) {
            details = split[1];
        } else {
            details = "";
        }

        return details;
    }

    private static int getIndex(String details) throws CharmieException {
        try {
            String index = details.replaceAll("[^0-9].*", "");

            return Integer.parseInt(index) - 1;
        } catch (NumberFormatException e) {
            throw new CharmieException("Please give a valid task number.");
        }
    }

    private static Pair<String, String> getFieldAndValue(String details) throws CharmieException {
        String[] split = details.trim().split(" ", 3);

        if (split.length < 3) {
            throw new CharmieException("Please provide a valid update command format: update [index] /[field] [value]");
        }

        if (!split[1].startsWith("/")) {
            throw new CharmieException("Please provide a valid field starting with '/'");
        }

        String field = split[1].substring(1).trim();
        String value = split[2].trim();

        if (field.isEmpty()) {
            throw new CharmieException("Please give a valid field to update.");
        }

        if (value.isEmpty()) {
            throw new CharmieException("Please give a valid value to update.");
        }

        return new Pair<>(field, value);
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
        assert input != null && !input.trim().isEmpty() : "input cannot be null or empty";

        String instruction = getInstruction(input);
        String details = getDetails(input);

        assert instruction != null && !instruction.isEmpty() : "instruction should be extracted";
        assert details != null : "details should never be null";

        Command command = null;
        switch (instruction) {
        case "bye":
            command = new ExitCommand();
            break;

        case "list":
            command = new ListCommand();
            break;

        case "delete":
            command = new DeleteCommand(getIndex(details));
            break;

        case "mark":
            command = new MarkCommand(getIndex(details));
            break;

        case "unmark":
            command = new UnmarkCommand(getIndex(details));
            break;

        case "find":
            command = new FindCommand(details);
            break;

        case "update":
            command = new UpdateCommand(getIndex(details), getFieldAndValue(details));
            break;

        default:
            Task task = parseTask(instruction, details);
            if (task == null) {
                throw new CharmieException("OOPS!!! I don't know what that means :-(");
            }
            command = new AddCommand(task);
        }
        assert command != null : "Command should be created";
        return command;
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
        assert instruction != null : "instruction cannot be null";
        assert details != null : "details cannot be null";

        Task task = null;
        switch (instruction) {
        case "todo":
            if (details.isEmpty()) {
                throw new CharmieException("OOPS!!! The description of a todo cannot be empty :(");
            }

            task = new ToDo(details);
            break;

        case "deadline":
            String[] d = details.split("/by", 2);
            if (d.length < 2 || d[0].trim().isEmpty() || d[1].trim().isEmpty()) {
                throw new CharmieException("OOPS!!! I need more details for your deadline :(");
            }

            task = new Deadline(d[0].trim(), d[1].trim());
            break;

        case "event":
            String[] e = details.split("/", 3);
            if (e.length < 3) {
                throw new CharmieException("OOPS!!! I need more details for your event :(");
            }

            String desc = e[0].trim();
            String from = e[1].replace("from", "").trim();
            String to = e[2].replace("to", "").trim();

            task = new Event(desc, from, to);
            break;

        default:
            return null;
        }

        return task;
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
        assert line != null && !line.isEmpty() : "line cannot be null or empty";

        String[] parts = line.split("\\s*\\|\\s*");

        if (parts.length < 3) {

            return null;
        }

        assert parts[0] != null : "type should not be null";
        assert parts[1] != null : "done status should not be null";
        assert parts[2] != null : "description should not be null";

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

