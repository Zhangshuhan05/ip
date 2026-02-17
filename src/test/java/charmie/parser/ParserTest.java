package charmie.parser;

import charmie.command.*;
import charmie.exception.CharmieException;
import charmie.task.Deadline;
import charmie.task.Event;
import charmie.task.Task;
import charmie.task.ToDo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    // ========================
    // getInstruction / getDetails
    // ========================

    @Test
    void testGetInstruction() {
        assertEquals("todo", Parser.getInstruction("todo buy groceries"));
        assertEquals("list", Parser.getInstruction("list"));
        assertEquals("delete", Parser.getInstruction("delete 1"));
    }

    @Test
    void testGetInstructionWithSpaces() {
        assertEquals("todo", Parser.getInstruction("   todo buy milk   "));
    }

    @Test
    void testGetDetails() {
        assertEquals("buy groceries", Parser.getDetails("todo buy groceries"));
        assertEquals("", Parser.getDetails("list"));
        assertEquals("1", Parser.getDetails("delete 1"));
    }

    @Test
    void testGetDetailsExtraSpaces() {
        assertEquals("buy milk", Parser.getDetails("todo    buy milk"));
    }

    // ========================
    // Command parsing (valid)
    // ========================

    @Test
    void testParseExitCommand() throws CharmieException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void testParseListCommand() throws CharmieException {
        Command command = Parser.parse("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void testParseDeleteCommand() throws CharmieException {
        Command command = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void testParseMarkCommand() throws CharmieException {
        Command command = Parser.parse("mark 2");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    void testParseUnmarkCommand() throws CharmieException {
        Command command = Parser.parse("unmark 3");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    void testParseFindCommand() throws CharmieException {
        Command command = Parser.parse("find book");
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    void testParseUpdateCommand() throws CharmieException {
        Command command = Parser.parse("update 1 /desc new description");
        assertInstanceOf(UpdateCommand.class, command);
    }

    @Test
    void testParseToDoCommand() throws CharmieException {
        Command command = Parser.parse("todo buy groceries");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void testParseDeadlineCommand() throws CharmieException {
        Command command = Parser.parse("deadline submit project /by 2024-12-31");
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void testParseEventCommand() throws CharmieException {
        Command command = Parser.parse("event meeting /from 2pm /to 3pm");
        assertInstanceOf(AddCommand.class, command);
    }

    // ========================
    // Command parsing (invalid)
    // ========================

    @Test
    void testParseInvalidCommand() {
        assertThrows(CharmieException.class,
            () -> Parser.parse("invalid command"));
    }

    @Test
    void testDeleteInvalidIndex() {
        assertThrows(CharmieException.class,
            () -> Parser.parse("delete abc"));
    }

    @Test
    void testMarkMissingIndex() {
        assertThrows(CharmieException.class,
            () -> Parser.parse("mark "));
    }

    // ========================
    // Update edge cases
    // ========================

    @Test
    void testUpdateMissingSlash() {
        assertThrows(CharmieException.class,
            () -> Parser.parse("update 1 desc new"));
    }

    @Test
    void testUpdateMissingField() {
        assertThrows(CharmieException.class,
            () -> Parser.parse("update 1 / new"));
    }

    @Test
    void testUpdateMissingValue() {
        assertThrows(CharmieException.class,
            () -> Parser.parse("update 1 /desc"));
    }

    // ========================
    // parseTask()
    // ========================

    @Test
    void testParseTaskToDo() throws CharmieException {
        Task task = Parser.parseTask("todo", "buy milk");
        assertInstanceOf(ToDo.class, task);
    }

    @Test
    void testParseTaskDeadline() throws CharmieException {
        Task task = Parser.parseTask("deadline", "submit /by 2024-12-31");
        assertInstanceOf(Deadline.class, task);
    }

    @Test
    void testParseTaskEvent() throws CharmieException {
        Task task = Parser.parseTask("event", "meeting /from 2pm /to 3pm");
        assertInstanceOf(Event.class, task);
    }

    @Test
    void testParseTaskUnknownType() throws CharmieException {
        Task task = Parser.parseTask("unknown", "something");
        assertNull(task);
    }

    // ========================
    // parseTaskFromFile()
    // ========================

    @Test
    void testParseTaskFromFileToDoNotDone() {
        Task task = Parser.parseTaskFromFile("T | 0 | buy groceries");
        assertNotNull(task);
        assertInstanceOf(ToDo.class, task);
        assertFalse(task.getDone());
    }

    @Test
    void testParseTaskFromFileToDoDone() {
        Task task = Parser.parseTaskFromFile("T | 1 | buy groceries");
        assertNotNull(task);
        assertTrue(task.getDone());
    }

    @Test
    void testParseTaskFromFileDeadline() {
        Task task = Parser.parseTaskFromFile(
            "D | 0 | submit project | 2024-12-31");
        assertNotNull(task);
        assertInstanceOf(Deadline.class, task);
    }

    @Test
    void testParseTaskFromFileEvent() {
        Task task = Parser.parseTaskFromFile(
            "E | 0 | meeting | 2pm 3pm");
        assertNotNull(task);
        assertInstanceOf(Event.class, task);
    }

    @Test
    void testParseTaskFromFileInvalidFormat() {
        Task task = Parser.parseTaskFromFile("T | invalid");
        assertNull(task);
    }

    @Test
    void testParseTaskFromFileInvalidType() {
        Task task = Parser.parseTaskFromFile("X | 0 | something");
        assertNull(task);
    }

    @Test
    void testParseTaskFromFileIncompleteEventTime() {
        Task task = Parser.parseTaskFromFile("E | 0 | meeting | onlyOneTime");
        assertNull(task);
    }
}
