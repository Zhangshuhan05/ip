package charmie.ui;

import java.util.List;
import java.util.Scanner;

import charmie.task.Task;
import charmie.task.TaskList;

/**
 * Represents the user interface layer for the Charmie chatbot.
 * <p>
 * This class is responsible for:
 * \- Reading user input from the console.
 * \- Formatting all user\-visible messages (for console or GUI display).
 * </p>
 */
public class Ui {
    /**
     * Scanner used to read user input from standard input.
     */
    private final Scanner scanner;

    /**
     * Constructs a new {@code Ui} instance with a {@link Scanner}
     * for reading user input from {@code System.in}.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a single line of user input from the console.
     *
     * @return the input line if one is available, or an empty string if no more
     *         input can be read
     */
    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    /**
     * Returns the welcome message shown when Charmie starts.
     *
     * @return a welcome message string
     */
    public String welcomeMsg() {
        return "Hello! I'm Charmie.\nWhat can I do for you? ;)";
    }

    /**
     * Returns the goodbye message shown when Charmie exits.
     *
     * @return a farewell message string
     */
    public String goodbyeMsg() {
        return "Bye! Hope to see you again soon!";
    }

    /**
     * Returns the formatted message displayed after a task is added.
     *
     * @param task      the task that was added
     * @param taskCount the total number of tasks after adding this task
     * @return a formatted confirmation message
     */
    public String addTaskMsg(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task.getString()
            + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns the formatted message displayed after a task is deleted.
     *
     * @param task      the task that was deleted
     * @param taskCount the total number of tasks after deleting this task
     * @return a formatted confirmation message
     */
    public String delTaskMsg(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task.getString()
            + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /**
     * Returns the formatted message displayed after a task is marked as done.
     *
     * @param task the task that was marked as done
     * @return a formatted confirmation message
     */
    public String markTaskMsg(Task task) {
        return "Nice! I've marked this task as done:\n  "
            + task.getString();
    }

    /**
     * Returns the formatted message displayed after a task is unmarked
     * (set as not done).
     *
     * @param task the task that was unmarked
     * @return a formatted confirmation message
     */
    public String unmarkTaskMsg(Task task) {
        return "OKY, I've marked this task as not done yet:\n  ["
            + task.getStatusIcon() + "] " + task.getDescription();
    }

    /**
     * Returns a formatted string listing all tasks in the given {@link TaskList}.
     *
     * @param tasks the list of tasks to display
     * @return a formatted list of tasks, or a message indicating that the list is empty
     */
    public String listTasks(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "Your task list is empty.";
        }

        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");

        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append(i + 1).append(".")
                .append(tasks.getTask(i).getString()).append("\n");
        }

        return sb.toString().trim();
    }

    /**
     * Returns a formatted string listing all tasks that match a given keyword.
     *
     * @param matches the list of tasks that matched the search
     * @param keyword the keyword used for searching
     * @return a formatted list of matching tasks, or a message indicating that none were found
     */
    public String findTasksMsg(List<Task> matches, String keyword) {
        if (matches.isEmpty()) {
            return "No tasks found matching \"" + keyword + "\"";
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");

        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(".")
                .append(matches.get(i).getString()).append("\n");
        }

        return sb.toString().trim();
    }

    /**
     * Formats an exception into a user\-friendly string for display.
     *
     * @param e the exception that occurred
     * @return the exception message
     */
    public String showException(Exception e) {
        return e.getMessage();
    }

    /**
     * Returns the formatted message displayed after a task is updated.
     *
     * @param oldTask the original task before the update
     * @param newTask the updated task
     * @return a formatted confirmation message describing the change
     */
    public String updateTaskMsg(Task oldTask, Task newTask) {
        return "OKY, I've updated this task:\n  " + oldTask.getString()
            + "\nto:\n  " + newTask.getString();
    }
}
