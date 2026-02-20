package charmie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task in Charmie with a description, start date/time, and end date/time.
 * <p>
 * Supports multiple input date/time formats: "yyyy-MM-dd HHmm", "yyyy-MM-dd",
 * and ISO local date-time. Overrides Task methods to include start/end information
 * in display and storage formats.
 */
public class Event extends Task {

    /** Input date/time formats that are accepted when creating an Event. */
    private static final DateTimeFormatter[] INPUT_FORMATS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };

    /** Output format used for displaying the start and end date/times. */
    private static final DateTimeFormatter OUTPUT_FORMAT =
        DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma");

    /** Start date/time of the event. */
    protected LocalDateTime start;

    /** End date/time of the event. */
    protected LocalDateTime end;

    /**
     * Constructs an Event task with a description, start date/time, and end date/time.
     *
     * @param description the description of the event task
     * @param start the start date/time as a string
     * @param end the end date/time as a string
     * @throws IllegalArgumentException if either date/time format is invalid
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = parseDateTime(start.trim());
        this.end = parseDateTime(end.trim());
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
            } catch (DateTimeParseException e) {
                try {
                    LocalDate date = LocalDate.parse(input, formatter);
                    return date.atStartOfDay();
                } catch (DateTimeParseException ignored) {
                    // Continue to next formatter
                }
            }
        }
        throw new IllegalArgumentException(
            "PLease use [/from yyyy-MM-dd or yyyy-MM-dd HHmm /to yyyy-MM-dd or yyyy-MM-dd HHmm] for date/time inputs!");
    }

    /**
     * Returns the string representation of this Event task for display.
     * Includes type "[E]", status, description, start, and end date/times.
     *
     * @return a string representing this Event task for display
     */
    @Override
    public String getString() {
        return "[E]" + "[" + getStatusIcon() + "] " + description
            + " (from: " + start.format(OUTPUT_FORMAT)
            + " to: " + end.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Converts this Event task into a string suitable for saving to a file.
     * Format: E | [done status] | [description] | [start] [end]
     *
     * @return a string representing this task for storage
     */
    @Override
    public String saveToTaskList() {
        return "E | " + (isDone ? "1" : "0")
            + " | " + description + " | " + start + " " + end;
    }

    /**
     * Creates a new Event task with an updated field.
     * <p>
     * Supports updating "name" (description), "from" (start), or "to" (end).
     *
     * @param field the field to update ("name", "from", or "to")
     * @param newValue the new value for the field
     * @return a new Event task with the updated field
     * @throws IllegalArgumentException if the field is invalid
     */
    @Override
    public Task update(String field, String newValue) {
        switch (field.toLowerCase()) {
        case "name":
            return new Event(newValue, start.toString(), end.toString());
        case "from":
            return new Event(description, newValue, end.toString());
        case "to":
            return new Event(description, start.toString(), newValue);
        default:
            throw new IllegalArgumentException(
                "Invalid field! Valid fields are: /name, /start, /end.");
        }
    }
}
