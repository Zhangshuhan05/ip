import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Charmie {
    private static final String PATH = "data/duke.txt";

    private static void addTaskMsg(String line, String indent, Task task, int taskCount) {
        System.out.println(line);
        System.out.println(indent + "Got it. I've added this task:");
        System.out.println(indent + "  " + task.getString());
        System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");
        System.out.println(line);
        System.out.println();
    } // for easy calling of the add task messages

    private static void delTaskMsg(String line, String indent, Task task, int taskCount) {
        System.out.println(line);
        System.out.println(indent +  "Noted. I've removed this task:");
        System.out.println(indent + "  " + task.getString());
        System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");
        System.out.println(line);
        System.out.println();
    } // for easy calling of the add task messages


    public static void main(String[] args) {
        String INDENT = "    ";
        String line = INDENT + "____________________________________________________________";

        TaskList tasks = new TaskList();
        tasks.loadFromFile();

        System.out.println(line);
        System.out.println(INDENT + "Hello! I'm Charmie");
        System.out.println(INDENT + "What can I do for you? ;)");
        System.out.println(line);
        System.out.println();
        Scanner reader = new Scanner(System.in);

        while (true) {
            try {
                String input = reader.nextLine();
                Scanner inputScanner = new Scanner(input);
                String instruction = inputScanner.next(); // first word

                if (instruction.equals("bye")) {
                    System.out.println(line);
                    System.out.println(INDENT + "Byee. Hope to see you again soon!");
                    System.out.println(line);
                    return;

                } else if (instruction.equals("list")) {
                    System.out.println(line);
                    System.out.println(INDENT + "Here are the tasks in your list:");
                    for (int i = 0; i < tasks.getSize(); i++) {
                        Task task = tasks.getTask(i);
                        System.out.print(INDENT + (i + 1) + ".");
                        System.out.println(task.getString());
                    }
                    System.out.println(line);
                    System.out.println();
                } else if (instruction.equals("todo")) {
                    String task = inputScanner.hasNextLine() ? inputScanner.nextLine().trim() : "";
                    if (task.isEmpty()) {
                        throw new CharmieException("OOPS!!! The description of a todo cannot be empty :(");
                    }

                    ToDo toDo = new ToDo(task);
                    tasks.addTask(toDo);
                    tasks.saveToDatabase();
                    addTaskMsg(line, INDENT, toDo, tasks.getSize());
                } else if (instruction.equals("deadline")) {
                    String task = inputScanner.hasNextLine() ? inputScanner.nextLine().trim() : "";
                    String[] params = task.split("/by", 2);
                    if (params.length < 2 || params[0].trim().isEmpty() || params[1].trim().isEmpty()) {
                        throw new CharmieException("OOPS!!! I need more details for your deadline :(");
                    }
                    String description = params[0].trim();
                    String by = params[1].trim();

                    Deadline deadline = new Deadline(description, by);
                    tasks.addTask(deadline);
                    tasks.saveToDatabase();
                    addTaskMsg(line, INDENT, deadline, tasks.getSize());
                } else if (instruction.equals("event")) {
                    String task = inputScanner.hasNextLine() ? inputScanner.nextLine().trim() : "";
                    String[] params = task.split("/", 3);
                    if (params.length < 3 || params[0].trim().isEmpty() || params[1].trim().isEmpty() || params[2].trim().isEmpty()) {
                        throw new CharmieException("OOPS!!! I need more details for your event :(");
                    }
                    String description = params[0].trim();
                    String start = params[1].trim().substring(5).trim();
                    String end = params[2].trim().substring(3).trim();

                    Event event = new Event(description, start, end);
                    tasks.addTask(event);
                    tasks.saveToDatabase();
                    addTaskMsg(line, INDENT, event, tasks.getSize());
                } else if (instruction.equals("delete")) {
                    int index = inputScanner.nextInt() - 1;
                    if (index >= 0 && index < tasks.getSize()) {
                        Task toDel = tasks.getTask(index);
                        tasks.removeTask(index);
                        tasks.saveToDatabase();
                        delTaskMsg(line, INDENT, toDel, tasks.getSize());
                    } else {
                        throw new CharmieException("Invalid number, try again.");
                    }

                } else if (instruction.equals("mark")) {
                    int index = inputScanner.nextInt() - 1;
                    if (index >= 0 && index < tasks.getSize()) {
                        Task marked = tasks.markTask(index);
                        tasks.saveToDatabase();
                        System.out.println(line);
                        System.out.println(INDENT + "Nicee! I've marked this task as done:");
                        System.out.println(INDENT + marked.getString()); // !!
                        System.out.println(line);
                        System.out.println();
                    } else {
                        throw new CharmieException("Invalid number, try again.");
                    }
                } else if (instruction.equals("unmark")) {
                    int index = inputScanner.nextInt() - 1;
                    if (index >= 0 && index < tasks.getSize()) {
                        Task unmarked = tasks.unmarkTask(index);
                        tasks.saveToDatabase();
                        System.out.println(line);
                        System.out.println(INDENT + "OKY, I've marked this task as not done yet:");
                        System.out.println(INDENT + "[" + unmarked.getStatusIcon() + "]" + unmarked.description);
                        System.out.println(line);
                        System.out.println();
                    } else {
                        throw new CharmieException("Invalid number, try again.");
                    }
                } else {
                    throw new CharmieException("OOPS!!! I don't know what that means :-(, can you be more specific?");
                }
            } catch (CharmieException e) {
                System.out.println(line);
                System.out.println(INDENT + e.getMessage());
                System.out.println(line);
                System.out.println();
            }
    }
}}
