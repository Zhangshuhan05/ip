package charmie.command;

import charmie.exception.CharmieException;
import charmie.storage.Storage;
import charmie.task.TaskList;
import charmie.task.ToDo;
import charmie.ui.Ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for command classes.
 * Tests command execution and behavior.
 */
public class CommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() throws Exception {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("./test_data.txt");
    }

    // ========================
    // ListCommand Tests
    // ========================

    @Test
    void testListCommandEmptyList() throws CharmieException {
        ListCommand cmd = new ListCommand();
        String response = cmd.run(tasks, ui, storage);
        assertNotNull(response);
        assertFalse(cmd.isExit());
    }

    @Test
    void testListCommandWithTasks() throws CharmieException {
        tasks.addTask(new ToDo("task 1"));
        tasks.addTask(new ToDo("task 2"));

        ListCommand cmd = new ListCommand();
        String response = cmd.run(tasks, ui, storage);

        assertNotNull(response);
        assertTrue(response.contains("task 1"));
        assertTrue(response.contains("task 2"));
    }

    // ========================
    // ExitCommand Tests
    // ========================

    @Test
    void testExitCommandIsExit() throws CharmieException {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.isExit());
    }

    @Test
    void testExitCommandRun() throws CharmieException {
        ExitCommand cmd = new ExitCommand();
        String response = cmd.run(tasks, ui, storage);
        assertNotNull(response);
    }

    // ========================
    // MarkCommand Tests
    // ========================

    @Test
    void testMarkCommandMarkTask() throws CharmieException {
        tasks.addTask(new ToDo("task to mark"));
        MarkCommand cmd = new MarkCommand(0);

        String response = cmd.run(tasks, ui, storage);

        assertTrue(tasks.getTask(0).getDone());
        assertNotNull(response);
        assertFalse(cmd.isExit());
    }

    @Test
    void testMarkCommandMultipleTasks() throws CharmieException {
        tasks.addTask(new ToDo("task 1"));
        tasks.addTask(new ToDo("task 2"));

        new MarkCommand(0).run(tasks, ui, storage);
        new MarkCommand(1).run(tasks, ui, storage);

        assertTrue(tasks.getTask(0).getDone());
        assertTrue(tasks.getTask(1).getDone());
    }

    // ========================
    // UnmarkCommand Tests
    // ========================

    @Test
    void testUnmarkCommandUnmarkTask() throws CharmieException {
        tasks.addTask(new ToDo("task"));
        tasks.markTask(0);

        UnmarkCommand cmd = new UnmarkCommand(0);
        String response = cmd.run(tasks, ui, storage);

        assertFalse(tasks.getTask(0).getDone());
        assertNotNull(response);
        assertFalse(cmd.isExit());
    }

    @Test
    void testUnmarkAlreadyUnmarked() throws CharmieException {
        tasks.addTask(new ToDo("task"));

        UnmarkCommand cmd = new UnmarkCommand(0);
        String response = cmd.run(tasks, ui, storage);

        assertFalse(tasks.getTask(0).getDone());
        assertNotNull(response);
    }

    // ========================
    // DeleteCommand Tests
    // ========================

    @Test
    void testDeleteCommandRemoveTask() throws CharmieException {
        tasks.addTask(new ToDo("task to delete"));
        tasks.addTask(new ToDo("task to keep"));

        DeleteCommand cmd = new DeleteCommand(0);
        String response = cmd.run(tasks, ui, storage);

        assertEquals(1, tasks.getSize());
        assertTrue(tasks.getTask(0).getDescription().contains("keep"));
        assertNotNull(response);
        assertFalse(cmd.isExit());
    }

    @Test
    void testDeleteMultipleTasks() throws CharmieException {
        tasks.addTask(new ToDo("task 1"));
        tasks.addTask(new ToDo("task 2"));
        tasks.addTask(new ToDo("task 3"));

        new DeleteCommand(0).run(tasks, ui, storage);
        assertEquals(2, tasks.getSize());

        new DeleteCommand(0).run(tasks, ui, storage);
        assertEquals(1, tasks.getSize());
    }

    // ========================
    // FindCommand Tests
    // ========================

    @Test
    void testFindCommandSingleMatch() throws CharmieException {
        tasks.addTask(new ToDo("buy milk"));
        tasks.addTask(new ToDo("do homework"));

        FindCommand cmd = new FindCommand("milk");
        String response = cmd.run(tasks, ui, storage);

        assertNotNull(response);
        assertTrue(response.contains("milk"));
        assertFalse(cmd.isExit());
    }

    @Test
    void testFindCommandMultipleMatches() throws CharmieException {
        tasks.addTask(new ToDo("buy milk"));
        tasks.addTask(new ToDo("buy eggs"));
        tasks.addTask(new ToDo("do homework"));

        FindCommand cmd = new FindCommand("buy");
        String response = cmd.run(tasks, ui, storage);

        assertNotNull(response);
        assertTrue(response.contains("buy"));
    }

    @Test
    void testFindCommandNoMatches() throws CharmieException {
        tasks.addTask(new ToDo("buy milk"));
        tasks.addTask(new ToDo("do homework"));

        FindCommand cmd = new FindCommand("xyz");
        String response = cmd.run(tasks, ui, storage);

        assertNotNull(response);
    }
}
