package charmie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task in Charmie with a description and a due date/time.
 * <p>
 * Supports multiple input date/time formats: "yyyy-MM-dd HHmm", "yyyy-MM-dd",
 * and ISO local date-time. Overrides Task methods to include the deadline
 * information in display and storage formats.
 */
public class Deadline extends Task {

    /** Input date/time formats that are accepted when creating a Deadline. */
    private static final DateTimeFormatter[] INPUT_FORMATS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };

    /** Output format used for displaying the deadline. */
    private static final DateTimeFormatter OUTPUT_FORMAT =
        DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma");

    /** The due date/time of this deadline task. */
    protected LocalDateTime by;

    /**
     * Constructs a Deadline task with a description and due date/time.
     *
     * @param description the description of the deadline task
     * @param by the due date/time as a string
     * @throws IllegalArgumentException if the date/time format is invalid
     */
    public Deadline(String description, String by) throws IllegalArgumentException {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Parses a date/time string into a {@link LocalDateTime}.
     * <p>
     * Tries multiple formats and returns the first successful parse. If only a date
     * is provided, the time defaults to start of day.
     *
     * @param input the date/time string to parse
     * @return a LocalDateTime representing the parsed date/time
     * @throws IllegalArgumentException if the input cannot be parsed in any supported format
     */
    private static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e1) {
                try {
                    LocalDate date = LocalDate.parse(input, formatter);
                    return date.atStartOfDay();
                } catch (DateTimeParseException e2) {
                    // continue to next formatter
                }
            }
        }
        throw new IllegalArgumentException(
            "Invalid date format! Use yyyy-MM-dd or yyyy-MM-dd HHmm");
    }

    /**
     * Returns the string representation of this Deadline task for display.
     * Includes type "[D]", status, description, and formatted due date/time.
     *
     * @return a string representing this Deadline task for display
     */
    @Override
    public String getString() {
        return "[D]" + "[" + getStatusIcon() + "] "
            + description + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Converts this Deadline task into a string suitable for saving to a file.
     * Format: D | [done status] | [description] | [due date/time]
     *
     * @return a string representing this task for storage
     */
    @Override
    public String saveToTaskList() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }

    /**
     * Creates a new Deadline task with an updated field.
     * <p>
     * Supports updating "name" (description) or "by" (due date/time).
     *
     * @param field the field to update ("name" or "by")
     * @param newValue the new value for the field
     * @return a new Deadline task with the updated field
     * @throws IllegalArgumentException if the field is invalid
     */
    @Override
    public Task update(String field, String newValue) {
        switch (field.toLowerCase()) {
        case "name":
            return new Deadline(newValue, by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")));
        case "by":
            return new Deadline(description, newValue);
        default:
            throw new IllegalArgumentException("Invalid field for update: " + field);
        }
    }
}
