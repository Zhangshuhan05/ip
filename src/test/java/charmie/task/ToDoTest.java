package charmie.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the ToDo task class.
 * Tests all public methods and edge cases.
 */
public class ToDoTest {

    @Test
    void testToDoConstructor() {
        ToDo task = new ToDo("buy milk");
        assertEquals("buy milk", task.getDescription());
        assertFalse(task.getDone());
    }

    @Test
    void testGetString() {
        ToDo task = new ToDo("buy groceries");
        String result = task.getString();
        assertTrue(result.contains("[T]"));
        assertTrue(result.contains("buy groceries"));
        assertTrue(result.contains("[ ]")); // not done
    }

    @Test
    void testGetStringWhenDone() {
        ToDo task = new ToDo("buy groceries");
        task.markAsDone();
        String result = task.getString();
        assertTrue(result.contains("[X]")); // done
    }

    @Test
    void testSaveToTaskList() {
        ToDo task = new ToDo("submit assignment");
        String saved = task.saveToTaskList();
        assertEquals("T | 0 | submit assignment", saved);
    }

    @Test
    void testSaveToTaskListWhenDone() {
        ToDo task = new ToDo("submit assignment");
        task.markAsDone();
        String saved = task.saveToTaskList();
        assertEquals("T | 1 | submit assignment", saved);
    }

    @Test
    void testUpdate() {
        ToDo task = new ToDo("old description");
        Task updated = task.update("name", "new description");
        assertInstanceOf(ToDo.class, updated);
        assertEquals("new description", updated.getDescription());
    }

    @Test
    void testUpdateIgnoresField() {
        // ToDo.update() ignores the field parameter and always updates description
        ToDo task = new ToDo("original");
        Task updated = task.update("anything", "new");
        assertEquals("new", updated.getDescription());
    }

    @Test
    void testMarkAsDone() {
        ToDo task = new ToDo("task");
        task.markAsDone();
        assertTrue(task.getDone());
    }

    @Test
    void testUnMark() {
        ToDo task = new ToDo("task");
        task.markAsDone();
        task.unMark();
        assertFalse(task.getDone());
    }

    @Test
    void testGetStatusIcon() {
        ToDo task = new ToDo("task");
        assertEquals(" ", task.getStatusIcon());
        task.markAsDone();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    void testEmptyDescription() {
        ToDo task = new ToDo("");
        assertEquals("", task.getDescription());
    }

    @Test
    void testLongDescription() {
        String longDesc = "a".repeat(1000);
        ToDo task = new ToDo(longDesc);
        assertEquals(longDesc, task.getDescription());
    }

    @Test
    void testDescriptionWithSpecialCharacters() {
        String specialDesc = "Task with @#$%^&*() symbols!";
        ToDo task = new ToDo(specialDesc);
        assertEquals(specialDesc, task.getDescription());
    }
}
