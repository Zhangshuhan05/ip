package charmie.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the Deadline task class.
 * Tests date parsing, formatting, and task operations.
 */
public class DeadlineTest {

    @Test
    void testDeadlineConstructorWithFullDateTime() {
        Deadline task = new Deadline("submit project", "2024-12-31 1400");
        assertEquals("submit project", task.getDescription());
        assertFalse(task.getDone());
    }

    @Test
    void testDeadlineConstructorWithDateOnly() {
        Deadline task = new Deadline("submit project", "2024-12-31");
        assertEquals("submit project", task.getDescription());
        assertFalse(task.getDone());
    }

    @Test
    void testDeadlineInvalidDateFormat() {
        assertThrows(IllegalArgumentException.class, () ->
            new Deadline("submit", "invalid-date"));
    }

    @Test
    void testDeadlineInvalidDateFormatNoYear() {
        assertThrows(IllegalArgumentException.class, () ->
            new Deadline("submit", "31-12-2024"));
    }

    @Test
    void testGetString() {
        Deadline task = new Deadline("submit project", "2024-12-31 1400");
        String result = task.getString();
        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("submit project"));
        assertTrue(result.contains("by:"));
    }

    @Test
    void testGetStringIncludesStatus() {
        Deadline task = new Deadline("submit project", "2024-12-31");
        String result = task.getString();
        assertTrue(result.contains("[ ]")); // not done
        task.markAsDone();
        result = task.getString();
        assertTrue(result.contains("[X]")); // done
    }

    @Test
    void testSaveToTaskList() {
        Deadline task = new Deadline("submit", "2024-12-31 1400");
        String saved = task.saveToTaskList();
        assertTrue(saved.startsWith("D | 0 | submit |"));
    }

    @Test
    void testSaveToTaskListWhenDone() {
        Deadline task = new Deadline("submit", "2024-12-31");
        task.markAsDone();
        String saved = task.saveToTaskList();
        assertTrue(saved.startsWith("D | 1 | submit |"));
    }

    @Test
    void testUpdateName() {
        Deadline task = new Deadline("old name", "2024-12-31");
        Task updated = task.update("name", "new name");
        assertInstanceOf(Deadline.class, updated);
        assertEquals("new name", updated.getDescription());
    }

    @Test
    void testUpdateNameCaseInsensitive() {
        Deadline task = new Deadline("old", "2024-12-31");
        Task updated = task.update("NAME", "new");
        assertEquals("new", updated.getDescription());
    }

    @Test
    void testUpdateBy() {
        Deadline task = new Deadline("submit", "2024-12-31");
        Task updated = task.update("by", "2024-01-15");
        assertInstanceOf(Deadline.class, updated);
        assertEquals("submit", updated.getDescription());
    }

    @Test
    void testUpdateByWithInvalidDate() {
        Deadline task = new Deadline("submit", "2024-12-31");
        assertThrows(IllegalArgumentException.class, () ->
            task.update("by", "invalid-date"));
    }

    @Test
    void testUpdateInvalidField() {
        Deadline task = new Deadline("submit", "2024-12-31");
        assertThrows(IllegalArgumentException.class, () ->
            task.update("invalid", "value"));
    }

    @Test
    void testUpdateInvalidFieldCaseInsensitive() {
        Deadline task = new Deadline("submit", "2024-12-31");
        assertThrows(IllegalArgumentException.class, () ->
            task.update("INVALID", "value"));
    }

    @Test
    void testMarkAndUnmark() {
        Deadline task = new Deadline("submit", "2024-12-31");
        task.markAsDone();
        assertTrue(task.getDone());
        task.unMark();
        assertFalse(task.getDone());
    }

    @Test
    void testDateParsingVariousFormats() {
        // Test yyyy-MM-dd HHmm format
        Deadline task1 = new Deadline("task1", "2024-12-31 1400");
        assertNotNull(task1);

        // Test yyyy-MM-dd format
        Deadline task2 = new Deadline("task2", "2024-12-31");
        assertNotNull(task2);
    }

    @Test
    void testEmptyDescription() {
        Deadline task = new Deadline("", "2024-12-31");
        assertEquals("", task.getDescription());
    }

    @Test
    void testSpecialCharactersInDescription() {
        Deadline task = new Deadline("Submit @#$%^&*() report", "2024-12-31");
        assertEquals("Submit @#$%^&*() report", task.getDescription());
    }
}
