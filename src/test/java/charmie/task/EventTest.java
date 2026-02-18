package charmie.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the Event task class.
 * Tests date parsing, formatting, and event-specific operations.
 */
public class EventTest {

    @Test
    void testEventConstructorWithFullDateTime() {
        Event task = new Event("team meeting", "2024-12-31 1400", "2024-12-31 1500");
        assertEquals("team meeting", task.getDescription());
        assertFalse(task.getDone());
    }

    @Test
    void testEventConstructorWithDateOnly() {
        Event task = new Event("conference", "2024-12-31", "2025-01-01");
        assertEquals("conference", task.getDescription());
        assertFalse(task.getDone());
    }

    @Test
    void testEventInvalidStartDate() {
        assertThrows(IllegalArgumentException.class, () ->
            new Event("meeting", "invalid-date", "2024-12-31"));
    }

    @Test
    void testEventInvalidEndDate() {
        assertThrows(IllegalArgumentException.class, () ->
            new Event("meeting", "2024-12-31", "invalid-date"));
    }

    @Test
    void testEventBothDatesInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
            new Event("meeting", "bad-date", "worse-date"));
    }

    @Test
    void testGetString() {
        Event task = new Event("meeting", "2024-12-31 1400", "2024-12-31 1500");
        String result = task.getString();
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("meeting"));
        assertTrue(result.contains("from:"));
        assertTrue(result.contains("to:"));
    }

    @Test
    void testGetStringIncludesStatus() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        String result = task.getString();
        assertTrue(result.contains("[ ]")); // not done
        task.markAsDone();
        result = task.getString();
        assertTrue(result.contains("[X]")); // done
    }

    @Test
    void testSaveToTaskList() {
        Event task = new Event("meeting", "2024-12-31 1400", "2024-12-31 1500");
        String saved = task.saveToTaskList();
        assertTrue(saved.startsWith("E | 0 | meeting |"));
    }

    @Test
    void testSaveToTaskListWhenDone() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        task.markAsDone();
        String saved = task.saveToTaskList();
        assertTrue(saved.startsWith("E | 1 | meeting |"));
    }

    @Test
    void testUpdateName() {
        Event task = new Event("old name", "2024-12-31", "2025-01-01");
        Task updated = task.update("name", "new name");
        assertInstanceOf(Event.class, updated);
        assertEquals("new name", updated.getDescription());
    }

    @Test
    void testUpdateNameCaseInsensitive() {
        Event task = new Event("old", "2024-12-31", "2025-01-01");
        Task updated = task.update("NAME", "new");
        assertEquals("new", updated.getDescription());
    }

    @Test
    void testUpdateFrom() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        Task updated = task.update("from", "2024-01-15");
        assertInstanceOf(Event.class, updated);
        assertEquals("meeting", updated.getDescription());
    }

    @Test
    void testUpdateFromWithInvalidDate() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        assertThrows(IllegalArgumentException.class, () ->
            task.update("from", "invalid-date"));
    }

    @Test
    void testUpdateTo() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        Task updated = task.update("to", "2024-01-20");
        assertInstanceOf(Event.class, updated);
        assertEquals("meeting", updated.getDescription());
    }

    @Test
    void testUpdateToWithInvalidDate() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        assertThrows(IllegalArgumentException.class, () ->
            task.update("to", "invalid-date"));
    }

    @Test
    void testUpdateInvalidField() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        assertThrows(IllegalArgumentException.class, () ->
            task.update("invalid", "value"));
    }

    @Test
    void testUpdateInvalidFieldMessage() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        assertThrows(IllegalArgumentException.class, () ->
            task.update("duration", "2 hours"));
    }

    @Test
    void testMarkAndUnmark() {
        Event task = new Event("meeting", "2024-12-31", "2025-01-01");
        task.markAsDone();
        assertTrue(task.getDone());
        task.unMark();
        assertFalse(task.getDone());
    }

    @Test
    void testDateParsingVariousFormats() {
        // Test yyyy-MM-dd HHmm format
        Event task1 = new Event("meeting", "2024-12-31 1400", "2024-12-31 1500");
        assertNotNull(task1);

        // Test yyyy-MM-dd format
        Event task2 = new Event("conference", "2024-12-31", "2025-01-01");
        assertNotNull(task2);
    }

    @Test
    void testSameStartAndEndDate() {
        Event task = new Event("all day event", "2024-12-31", "2024-12-31");
        assertEquals("all day event", task.getDescription());
    }

    @Test
    void testEmptyDescription() {
        Event task = new Event("", "2024-12-31", "2025-01-01");
        assertEquals("", task.getDescription());
    }

    @Test
    void testSpecialCharactersInDescription() {
        Event task = new Event("Team @Meeting #2024 &Review!", "2024-12-31", "2025-01-01");
        assertEquals("Team @Meeting #2024 &Review!", task.getDescription());
    }

    @Test
    void testEventWithWhitespaceInDates() {
        // Event constructor trims start and end dates
        Event task = new Event("meeting", "  2024-12-31  ", "  2025-01-01  ");
        assertNotNull(task);
    }
}
