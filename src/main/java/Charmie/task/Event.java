package Charmie.task;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    private static final DateTimeFormatter[] INPUT_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            //DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME
    };

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy hh:mma");


    Event(String description, String start, String end) {
        super(description);
        this.start = parseDateTime(start.trim());
        this.end = parseDateTime(end.trim());
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
        return "[E]" + "[" + getStatusIcon() + "] " + description
                + " (from: " + start.format(OUTPUT_FORMAT) + " to: " + end.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String saveToTaskList() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start + " " + end;
    }
}
