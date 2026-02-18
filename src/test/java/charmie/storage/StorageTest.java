package charmie.storage;

import charmie.task.TaskList;
import charmie.task.ToDo;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    private Storage createTempStorage() throws IOException {
        File tempFile = File.createTempFile("charmie_test", ".txt");
        tempFile.deleteOnExit();
        return new Storage(tempFile.getAbsolutePath());
    }

    @Test
    void testSaveAndLoadSingleTask() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("buy milk"));

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(1, loaded.getSize());
        System.out.println("Loaded task: " + loaded.getTask(0).toString());
        assertTrue(loaded.getTask(0).getString().contains("[T][ ] buy milk"));
    }

    @Test
    void testSaveAndLoadMultipleTasks() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("task 1"));
        tasks.addTask(new ToDo("task 2"));

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(2, loaded.getSize());
    }

    // ========================
    // Edge Cases
    // ========================

    @Test
    void testLoadFromNonExistingFileReturnsEmptyList() throws IOException {
        File tempFile = File.createTempFile("charmie_test", ".txt");
        tempFile.delete(); // simulate file not existing

        Storage storage = new Storage(tempFile.getAbsolutePath());

        TaskList loaded = storage.loadFromFile();

        assertEquals(0, loaded.getSize());
    }

    @Test
    void testSaveEmptyTaskList() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(0, loaded.getSize());
    }

    // ========================
    // Invalid Data Handling
    // ========================

    @Test
    void testLoadIgnoresInvalidLines() throws IOException {
        File tempFile = File.createTempFile("charmie_test", ".txt");
        tempFile.deleteOnExit();

        java.io.FileWriter fw = new java.io.FileWriter(tempFile);
        fw.write("INVALID LINE\n");
        fw.write("T | 0 | valid task\n");
        fw.close();

        Storage storage = new Storage(tempFile.getAbsolutePath());

        TaskList loaded = storage.loadFromFile();

        assertEquals(1, loaded.getSize());
    }

    // ========================
    // Save and Load Different Task Types
    // ========================

    @Test
    void testSaveAndLoadDeadlineTask() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new charmie.task.Deadline("submit project", "2024-12-31"));

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(1, loaded.getSize());
        assertInstanceOf(charmie.task.Deadline.class, loaded.getTask(0));
    }

    @Test
    void testSaveAndLoadEventTask() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new charmie.task.Event("meeting", "2024-12-31", "2025-01-01"));

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(1, loaded.getSize());
        assertInstanceOf(charmie.task.Event.class, loaded.getTask(0));
    }

    @Test
    void testSaveAndLoadMixedTasks() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("todo task"));
        tasks.addTask(new charmie.task.Deadline("deadline task", "2024-12-31"));
        tasks.addTask(new charmie.task.Event("event task", "2024-12-31", "2025-01-01"));

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(3, loaded.getSize());
        assertInstanceOf(ToDo.class, loaded.getTask(0));
        assertInstanceOf(charmie.task.Deadline.class, loaded.getTask(1));
        assertInstanceOf(charmie.task.Event.class, loaded.getTask(2));
    }

    @Test
    void testSaveAndLoadMarkedTasks() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("task 1"));
        tasks.addTask(new ToDo("task 2"));

        tasks.markTask(0);

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertTrue(loaded.getTask(0).getDone());
        assertFalse(loaded.getTask(1).getDone());
    }

    // ========================
    // Large Data Tests
    // ========================

    @Test
    void testSaveAndLoadManyTasks() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        for (int i = 0; i < 100; i++) {
            tasks.addTask(new ToDo("task " + i));
        }

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(100, loaded.getSize());
    }

    @Test
    void testSaveAndLoadTasksWithSpecialCharacters() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("Buy @#$%^&*() items!"));
        tasks.addTask(new ToDo("Task with | pipe symbols"));
        tasks.addTask(new ToDo("Line\nbreak test"));

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(3, loaded.getSize());
    }

    // ========================
    // Edge Cases for File Operations
    // ========================

    @Test
    void testSaveToFileCreatesParentDirectories() throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        String newPath = tempDir + File.separator + "charmie_test_" + System.currentTimeMillis() + File.separator + "test.txt";

        Storage storage = new Storage(newPath);

        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("test"));

        storage.saveToFile(tasks);

        assertTrue(new File(newPath).exists());

        // Cleanup
        new File(newPath).delete();
        new File(newPath).getParentFile().delete();
    }

    @Test
    void testLoadAndSavePreservesData() throws IOException {
        Storage storage1 = createTempStorage();

        TaskList original = new TaskList();
        original.addTask(new ToDo("task 1"));
        original.addTask(new ToDo("task 2"));
        original.markTask(0);

        storage1.saveToFile(original);

        TaskList loaded = storage1.loadFromFile();

        Storage storage2 = createTempStorage();
        storage2.saveToFile(loaded);

        TaskList reloaded = storage2.loadFromFile();

        assertEquals(2, reloaded.getSize());
        assertTrue(reloaded.getTask(0).getDone());
    }
}
