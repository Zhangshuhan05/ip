package Charmie.Ui;

import Charmie.task.Task;
import Charmie.task.TaskList;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private final static String INDENT = "    ";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    public static void welcomeMsg() {
        System.out.println("Hello! I'm Charmie.Charmie");
        System.out.println("What can I do for you? ;)");
    }

    public static void goodbyeMsg() {
        System.out.println("Byee. Hope to see you again soon!");
    }

    public static void listTasks(TaskList tasklist) {
        //System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasklist.getSize(); i++) {
            Task task = tasklist.getTask(i);
            System.out.print(INDENT + (i + 1) + ".");
            System.out.println(task.getString());
        }
        System.out.println();
    }
    public static void addTaskMsg(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(INDENT + "  " + task.getString());
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println();
    }

    public static void delTaskMsg(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(INDENT + "  " + task.getString());
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println();
    }

    public static void markTaskMsg(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(INDENT + "  " + task.getString());
        System.out.println();
    }

    public static void unmarkTaskMsg(Task task) {
        System.out.println("OKY, I've marked this task as not done yet:");
        System.out.println(INDENT + "[" + task.getStatusIcon() + "]" + task.getDescription());
        System.out.println();
    }

    public static void showException(Exception e) {
        System.out.println(e.getMessage());
        System.out.println();
    }

    public void findTasksMsg(List<Task> matches, String keyword) {
        if (matches.isEmpty()) {
            System.out.println("No tasks found matching \"" + keyword + "\"");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            int count = 1;
            for (Task task : matches) {
                System.out.println(INDENT + count + "." + task.getString());
                count++;
            }
        }
    }
}


