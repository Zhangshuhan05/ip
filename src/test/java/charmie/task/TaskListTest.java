package charmie.task;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the TaskList class.
 * Tests all operations including add, remove, mark, unmark, find, and update.
 */
public class TaskListTest {

    @Test
    void testAddTaskAndSize() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));

        assertEquals(1, list.getSize());
    }

    @Test
    void testAddMultipleTasks() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));
        list.addTask(new ToDo("task 3"));

        assertEquals(3, list.getSize());
    }

    @Test
    void testEmptyListSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.getSize());
    }

    @Test
    void testRemoveTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));

        list.removeTask(0);

        assertEquals(1, list.getSize());
        assertTrue(list.getTask(0).getDescription().contains("task 2"));
    }

    @Test
    void testRemoveLastTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));

        list.removeTask(1);

        assertEquals(1, list.getSize());
        assertTrue(list.getTask(0).getDescription().contains("task 1"));
    }

    @Test
    void testRemoveMultipleTasks() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));
        list.addTask(new ToDo("task 3"));

        list.removeTask(1);
        assertEquals(2, list.getSize());
        list.removeTask(0);
        assertEquals(1, list.getSize());
    }

    @Test
    void testGetTask() {
        TaskList list = new TaskList();
        ToDo todo = new ToDo("test task");
        list.addTask(todo);

        Task retrieved = list.getTask(0);
        assertEquals("test task", retrieved.getDescription());
    }

    @Test
    void testGetTaskFromMiddle() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));
        list.addTask(new ToDo("task 3"));

        Task task = list.getTask(1);
        assertTrue(task.getDescription().contains("task 2"));
    }

    @Test
    void testMarkTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));

        Task task = list.markTask(0);

        assertTrue(task.getDone());
        assertTrue(list.getTask(0).getDone());
    }

    @Test
    void testMarkMultipleTasks() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));
        list.addTask(new ToDo("task 3"));

        list.markTask(0);
        list.markTask(2);

        assertTrue(list.getTask(0).getDone());
        assertFalse(list.getTask(1).getDone());
        assertTrue(list.getTask(2).getDone());
    }

    @Test
    void testUnmarkTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));

        list.markTask(0);
        Task task = list.unmarkTask(0);

        assertFalse(task.getDone());
        assertFalse(list.getTask(0).getDone());
    }

    @Test
    void testUnmarkAlreadyUnmarked() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));

        Task task = list.unmarkTask(0);

        assertFalse(task.getDone());
    }

    @Test
    void testFindTasksSingleMatch() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("buy milk"));
        list.addTask(new ToDo("do homework"));

        List<Task> found = list.findTasks("milk");

        assertEquals(1, found.size());
        assertTrue(found.get(0).getDescription().contains("milk"));
    }

    @Test
    void testFindTasksMultipleMatches() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("buy milk"));
        list.addTask(new ToDo("buy eggs"));
        list.addTask(new ToDo("do homework"));

        List<Task> found = list.findTasks("buy");

        assertEquals(2, found.size());
    }

    @Test
    void testFindTasksNoMatches() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("buy milk"));
        list.addTask(new ToDo("do homework"));

        List<Task> found = list.findTasks("xyz");

        assertEquals(0, found.size());
    }

    @Test
    void testFindTasksCaseInsensitive() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("Buy Milk"));
        list.addTask(new ToDo("do homework"));

        List<Task> found = list.findTasks("buy");

        assertEquals(1, found.size());
    }

    @Test
    void testFindTasksPartialMatch() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("homework assignment"));
        list.addTask(new ToDo("do homework"));

        List<Task> found = list.findTasks("home");

        assertEquals(2, found.size());
    }

    @Test
    void testFindTasksEmptyKeyword() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));

        List<Task> found = list.findTasks("");

        assertEquals(2, found.size()); // empty string matches all
    }

    @Test
    void testUpdateTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("old task"));

        list.updateTask(0, "description", "new task");

        assertEquals("new task", list.getTask(0).getDescription());
    }

    @Test
    void testUpdateTaskMultiple() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));

        list.updateTask(0, "description", "updated 1");
        list.updateTask(1, "description", "updated 2");

        assertEquals("updated 1", list.getTask(0).getDescription());
        assertEquals("updated 2", list.getTask(1).getDescription());
    }


    @Test
    void testTaskListWithDifferentTaskTypes() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("todo task"));
        list.addTask(new Deadline("deadline task", "2024-12-31"));
        list.addTask(new Event("event task", "2024-12-31", "2025-01-01"));

        assertEquals(3, list.getSize());
        assertInstanceOf(ToDo.class, list.getTask(0));
        assertInstanceOf(Deadline.class, list.getTask(1));
        assertInstanceOf(Event.class, list.getTask(2));
    }

    @Test
    void testTaskListPreserveOrderAfterOperations() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));
        list.addTask(new ToDo("task 2"));
        list.addTask(new ToDo("task 3"));

        list.markTask(0);
        list.updateTask(1, "description", "updated");

        assertEquals("task 1", list.getTask(0).getDescription());
        assertEquals("updated", list.getTask(1).getDescription());
        assertEquals("task 3", list.getTask(2).getDescription());
    }
}
