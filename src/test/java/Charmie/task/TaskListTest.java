package Charmie.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void constructor_newTaskList_sizeZero() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.getSize());
    }

    @Test
    public void addTask_addOneTask_sizeOne() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("read book");

        taskList.addTask(todo);

        assertEquals(1, taskList.getSize());
    }

    @Test
    public void getTask_taskExists_returnsCorrectTask() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("read book");
        taskList.addTask(todo);

        Task retrieved = taskList.getTask(0);

        assertEquals(todo, retrieved);
    }

    @Test
    public void markTask_taskExists_taskMarkedDone() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("read book");
        taskList.addTask(todo);

        Task marked = taskList.markTask(0);

        assertEquals("[T][X] read book", marked.getString());
    }

    @Test
    public void unmarkTask_taskExists_taskUnmarked() {
        TaskList taskList = new TaskList();
        Task todo = new ToDo("read book");
        taskList.addTask(todo);
        taskList.markTask(0);

        Task unmarked = taskList.unmarkTask(0);

        assertEquals("[T][ ] read book", unmarked.getString());
    }

    @Test
    public void removeTask_taskExists_sizeDecreases() {
        TaskList taskList = new TaskList();
        taskList.addTask(new ToDo("read book"));
        taskList.addTask(new ToDo("write code"));

        taskList.removeTask(0);

        assertEquals(1, taskList.getSize());
    }
}
