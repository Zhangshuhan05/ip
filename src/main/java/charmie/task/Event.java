package charmie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Child class Event represents an event task with a start and end date/time
 * */
public class Event extends Task {
    private static final DateTimeFormatter[] INPUT_FORMATS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma");

    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructs an Event task with a description, start date/time, and end date/time.
     *
     * Parses the provided start and end date/time strings into LocalDateTime objects.
     * Supports multiple date/time formats: "yyyy-MM-dd HHmm", "yyyy-MM-dd", and ISO local date-time.
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
                "Invalid date format! Use yyyy-MM-dd or yyyy-MM-dd HHmm"
        );
    }


    @Override
    public String getString() {
        return "[E]" + "[" + getStatusIcon() + "] " + description
                + " (from: " + start.format(OUTPUT_FORMAT) + " to: " + end.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String saveToTaskList() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start + " " + end;
    }
}
