package Charmie;

import Charmie.task.Task;
import Charmie.task.TaskList;

import java.util.Scanner;

public class Ui {
    private final static String INDENT = "    ";
    private final static String LINE = INDENT + "____________________________________________________________";
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
        System.out.println(LINE);
        System.out.println(INDENT + "Hello! I'm Charmie.Charmie");
        System.out.println(INDENT + "What can I do for you? ;)");
        System.out.println(LINE);
        System.out.println();
    }

    public static void goodbyeMsg() {
        System.out.println(LINE);
        System.out.println(INDENT + "Byee. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public static void listTasks(TaskList tasklist) {
        System.out.println(LINE);
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < tasklist.getSize(); i++) {
            Task task = tasklist.getTask(i);
            System.out.print(INDENT + (i + 1) + ".");
            System.out.println(task.getString());
        }
        System.out.println(LINE);
        System.out.println();
    }
    public static void addTaskMsg(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task.getString());
        System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
        System.out.println();
    } // for easy calling of the add task messages

    public static void delTaskMsg(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + "  " + task.getString());
        System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
        System.out.println();
    }

    public static void markTaskMsg(Task task) {
        System.out.println(LINE);
        System.out.println(INDENT + "Nice! I've marked this task as done:");
        System.out.println(INDENT + "  " + task.getString());
        System.out.println(LINE);
        System.out.println();
    }

    public static void unmarkTaskMsg(Task task) {
        System.out.println(LINE);
        System.out.println(INDENT + "OKY, I've marked this task as not done yet:");
        System.out.println(INDENT + "[" + task.getStatusIcon() + "]" + task.getDescription());
        System.out.println(LINE);
        System.out.println();
    }

    public static void showException(Exception e) {
        System.out.println(LINE);
        System.out.println(INDENT + e.getMessage());
        System.out.println(LINE);
        System.out.println();
    }
}


