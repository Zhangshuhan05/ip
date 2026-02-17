package charmie.task;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    void testAddTaskAndSize() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));

        assertEquals(1, list.getSize());
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
    void testMarkTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));

        Task task = list.markTask(0);

        assertTrue(task.getDone());
    }

    @Test
    void testUnmarkTask() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("task 1"));

        list.markTask(0);
        Task task = list.unmarkTask(0);

        assertFalse(task.getDone());
    }

    @Test
    void testFindTasks() {
        TaskList list = new TaskList();
        list.addTask(new ToDo("buy milk"));
        list.addTask(new ToDo("do homework"));

        List<Task> found = list.findTasks("milk");

        assertEquals(1, found.size());
    }
}
