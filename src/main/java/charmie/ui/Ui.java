package charmie.ui;

import java.util.List;
import java.util.Scanner;

import charmie.task.Task;
import charmie.task.TaskList;

/**
 * Ui class handles the formatting of messages for the Charmie chatbot.
 * All methods now return Strings, so commands can use them directly for
 * console or GUI display.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a new Ui instance with a Scanner for reading user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of user input from the console.
     *
     * @return the input line, or empty string if none
     */
    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    /** Returns the welcome message. */
    public String welcomeMsg() {
        return "Hello! I'm Charmie.\nWhat can I do for you? ;)";
    }

    /** Returns the goodbye message. */
    public String goodbyeMsg() {
        return "Bye! Hope to see you again soon!";
    }

    /** Returns the message when a task is added. */
    public String addTaskMsg(Task task, int taskCount) {
        return "Got it. I've added this task:\n  " + task.getString()
                + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /** Returns the message when a task is deleted. */
    public String delTaskMsg(Task task, int taskCount) {
        return "Noted. I've removed this task:\n  " + task.getString()
                + "\nNow you have " + taskCount + " tasks in the list.";
    }

    /** Returns the message when a task is marked as done. */
    public String markTaskMsg(Task task) {

        return "Nice! I've marked this task as done:\n  "
                + task.getString();
    }

    /** Returns the message when a task is unmarked (not done). */
    public String unmarkTaskMsg(Task task) {

        return "OKY, I've marked this task as not done yet:\n  ["
                + task.getStatusIcon() + "] " + task.getDescription();
    }

    /** Returns a formatted string of all tasks in the TaskList. */
    public String listTasks(TaskList tasks) {
        if (tasks.getSize() == 0) {

            return "Your task list is empty.";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append(i + 1).append(".").append(tasks.getTask(i).getString()).append("\n");
        }
        return sb.toString().trim();
    }

    /** Returns a formatted string of tasks that match a search keyword. */
    public String findTasksMsg(List<Task> matches, String keyword) {
        if (matches.isEmpty()) {

            return "No tasks found matching \"" + keyword + "\"";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append(i + 1).append(".").append(matches.get(i).getString()).append("\n");
        }
        return sb.toString().trim();
    }

    /** Formats an exception into a string for display. */
    public String showException(Exception e) {
        return e.getMessage();
    }
}
