package charmie.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for ToDo task
 * */
public class ToDoTest {
    @Test
    public void getString_notDoneTodo_correctFormat() {
        ToDo todo = new ToDo("read book");

        assertEquals("[T][ ] read book", todo.getString());
    }
/**
     * Test for saving a ToDo task that is not marked as done
     * */
    @Test
    public void saveToTaskList_notDoneTodo_correctFormat() {
        ToDo todo = new ToDo("read book");

        assertEquals("T | 0 | read book", todo.saveToTaskList());
    }

}

