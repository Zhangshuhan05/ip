package Charmie.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void getString_notDoneTodo_correctFormat() {
        ToDo todo = new ToDo("read book");

        assertEquals("[T][ ] read book", todo.getString());
    }

    @Test
    public void saveToTaskList_notDoneTodo_correctFormat() {
        ToDo todo = new ToDo("read book");

        assertEquals("T | 0 | read book", todo.saveToTaskList());
    }

}

