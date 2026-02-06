package Charmie.task;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * Child class Deadline represents a deadline task with a due date/time
 * */
public class Deadline extends Task {
    protected LocalDateTime by;

    private static final DateTimeFormatter[] INPUT_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            //DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma");

    public Deadline(String description, String by) throws IllegalArgumentException {
        super(description);
        this.by = parseDateTime(by);
    }

    private static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter formatter : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e1) {
                try {
                    LocalDate date = LocalDate.parse(input, formatter);
                    return date.atStartOfDay();
                } catch (DateTimeParseException e2) {

                }
            }
        }
        throw new IllegalArgumentException(
                "Invalid date format! Use yyyy-MM-dd or yyyy-MM-dd HHmm"
        );
    }

    @Override
    public String getString() {
        return "[D]" + "[" + getStatusIcon() + "] " + description + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String saveToTaskList() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
