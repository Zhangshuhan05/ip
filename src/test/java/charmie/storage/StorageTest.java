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

    // ========================
    // Save + Load Happy Path
    // ========================

    @Test
    void testSaveAndLoadSingleTask() throws IOException {
        Storage storage = createTempStorage();

        TaskList tasks = new TaskList();
        tasks.addTask(new ToDo("buy milk"));

        storage.saveToFile(tasks);

        TaskList loaded = storage.loadFromFile();

        assertEquals(1, loaded.getSize());
        assertTrue(loaded.getTask(0).toString().contains("buy milk"));
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
}
