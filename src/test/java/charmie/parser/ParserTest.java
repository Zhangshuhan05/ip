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
        Command command = Parser.parse("event meeting /from 2024-12-31 1400 /to 2024-12-31 1500");
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
        Task task = Parser.parseTask("event", "meeting /from 2024-12-31 1400 /to 2024-12-31 1500");
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
            "E | 0 | meeting | 2026-11-25T13:05 2026-12-14T00:00");
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

    // ========================
    // Additional Edge Cases
    // ========================

    @Test
    void testGetInstructionEmptyString() {
        assertEquals("", Parser.getInstruction(""));
    }

    @Test
    void testGetInstructionOnlySpaces() {
        assertEquals("", Parser.getInstruction("   "));
    }

    @Test
    void testGetDetailsOnlySpaces() {
        assertEquals("", Parser.getDetails("   "));
    }

    @Test
    void testGetInstructionSpecialCharacters() {
        assertEquals("@todo", Parser.getInstruction("@todo task"));
    }

    @Test
    void testParseToDoEmptyDescription() {
        assertThrows(CharmieException.class, () -> Parser.parse("todo"));
    }

    @Test
    void testParseToDoOnlySpaces() {
        assertThrows(CharmieException.class, () -> Parser.parse("todo   "));
    }

    @Test
    void testParseDeadlineInvalidDateFormat() {
        assertThrows(IllegalArgumentException.class, () ->
            Parser.parse("deadline submit /by invalid-date"));
    }

    @Test
    void testParseEventInvalidStartDate() {
        assertThrows(IllegalArgumentException.class, () ->
            Parser.parse("event meeting /from bad-date /to 2024-12-31 1500"));
    }

    @Test
    void testParseEventInvalidEndDate() {
        assertThrows(IllegalArgumentException.class, () ->
            Parser.parse("event meeting /from 2024-12-31 1400 /to bad-date"));
    }

    @Test
    void testParseEventMissingFrom() {
        assertThrows(CharmieException.class, () ->
            Parser.parse("event meeting /to 3pm"));
    }

    @Test
    void testParseEventMissingTo() {
        assertThrows(CharmieException.class, () ->
            Parser.parse("event meeting /from 2pm"));
    }

    @Test
    void testParseDeadlineMissingBy() {
        assertThrows(CharmieException.class, () ->
            Parser.parse("deadline submit"));
    }

    @Test
    void testUpdateInvalidIndex() {
        assertThrows(CharmieException.class, () ->
            Parser.parse("update abc /field value"));
    }

    @Test
    void testUpdateIndexWithLetters() {
        assertThrows(CharmieException.class, () ->
            Parser.parse("update 1a /field value"));
    }

    @Test
    void testUpdateNegativeIndex() {
        // Should parse successfully but negative index would cause issues later
        assertThrows(CharmieException.class, () ->
            Parser.parse("update -1 /field value"));
    }

    @Test
    void testDeleteWithExtraDetails() throws CharmieException {
        // delete command only takes index, extra text should be ignored
        Command cmd = Parser.parse("delete 1 extra text");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    @Test
    void testMarkWithExtraDetails() throws CharmieException {
        Command cmd = Parser.parse("mark 2 extra");
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    void testToDoWithSpecialCharacters() throws CharmieException {
        Task task = Parser.parseTask("todo", "Buy @#$%^&*() items!");
        assertInstanceOf(ToDo.class, task);
        assertEquals("Buy @#$%^&*() items!", task.getDescription());
    }

    @Test
    void testDeadlineWithSpecialCharacters() throws CharmieException {
        Task task = Parser.parseTask("deadline", "Submit @Report /by 2024-12-31");
        assertInstanceOf(Deadline.class, task);
    }

    @Test
    void testEventWithSpecialCharacters() throws CharmieException {
        Task task = Parser.parseTask("event", "Team @Meeting /from 2018-03-24 /to 2018-03-25");
        assertInstanceOf(Event.class, task);
    }

    @Test
    void testParseTaskEmptyDescription() {

        assertThrows(CharmieException.class, () -> Parser.parseTask("todo", ""));
    }

    @Test
    void testParseTaskOnlySpaces() {

        assertThrows(CharmieException.class, () -> Parser.parseTask("todo", "   "));
    }

    @Test
    void testParseTaskFromFileEmptyLine() {
        // parseTaskFromFile has an assertion that rejects empty lines
        assertThrows(AssertionError.class, () ->
            Parser.parseTaskFromFile(""));
    }

    @Test
    void testParseTaskFromFileOnlyPipes() {
        Task task = Parser.parseTaskFromFile("| | |");
        assertNull(task);
    }

    @Test
    void testParseTaskFromFileExtraSpaces() {
        Task task = Parser.parseTaskFromFile("T | 0 |   task with spaces   ");
        assertNotNull(task);
    }

    @Test
    void testParseTaskFromFileMixedCase() {
        Task task = Parser.parseTaskFromFile("t | 0 | task");
        assertNull(task); // Must be uppercase
    }

    @Test
    void testParseTaskFromFileInvalidDoneStatus() {
        // Parser doesn't strictly validate done status - it accepts any integer
        Task task = Parser.parseTaskFromFile("T | 2 | task");
        assertNotNull(task); // Will still parse
    }

    @Test
    void testParseDeadlineWithISO8601Format() throws CharmieException {
        Command cmd = Parser.parse("deadline submit /by 2024-12-31T14:00:00");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void testLongDescriptionParsing() throws CharmieException {
        String longDesc = "a".repeat(1000);
        Task task = Parser.parseTask("todo", longDesc);
        assertNotNull(task);
        assertEquals(longDesc, task.getDescription());
    }

    @Test
    void testUpdateWithSpecialCharactersInField() {
        assertThrows(CharmieException.class, () ->
            Parser.parse("update 1 /@field value"));
    }

    @Test
    void testUpdateFieldWithNumbers() throws CharmieException {
        // Field names with numbers might work or fail depending on implementation
        Command cmd = Parser.parse("update 1 /field123 newvalue");
        assertInstanceOf(UpdateCommand.class, cmd);
    }

    @Test
    void testMultipleCommandsWithLeadingTrailingSpaces() throws CharmieException {
        Command cmd1 = Parser.parse("   list   ");
        assertInstanceOf(ListCommand.class, cmd1);

        Command cmd2 = Parser.parse("   mark 1   ");
        assertInstanceOf(MarkCommand.class, cmd2);
    }
}
