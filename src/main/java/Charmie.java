import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Charmie {

    private static void addTaskMsg(String line, String indent, Task task, int taskCount) {
        System.out.println(line);
        System.out.println(indent + "Got it. I've added this task:");
        System.out.println(indent + "  " + task.getString());
        System.out.println(indent + "Now you have " + taskCount + " tasks in the list.");
        System.out.println(line);
        System.out.println();
    } // for easy calling of the add task messages


    public static void main(String[] args) {
        String INDENT = "    ";
        List<Task> list = new ArrayList<>();

        String line = INDENT + "____________________________________________________________";

        System.out.println(line);
        System.out.println(INDENT + "Hello! I'm Charmie");
        System.out.println(INDENT + "What can I do for you? ;)");
        System.out.println(line);
        System.out.println();
        Scanner reader = new Scanner(System.in);
        while (true) {
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
                for (int i = 0; i < list.size(); i++) {
                    Task task = list.get(i);
                    System.out.print(INDENT + (i+1) + ".");
                    System.out.println(task.getString());
                }
                System.out.println(line);
                System.out.println();
            } else if (instruction.equals("todo")) {
                String task = inputScanner.hasNextLine() ? inputScanner.nextLine().trim() : "";

                ToDos toDo = new ToDos(task);
                list.add(toDo);
                addTaskMsg(line, INDENT, toDo, list.size());
            } else if (instruction.equals("deadline")) {
                String task = inputScanner.hasNextLine() ? inputScanner.nextLine().trim() : "";
                String[] params = task.split("/by", 2);
                String description = params[0].trim();
                String by = params[1].trim();

                Deadline deadline = new Deadline(description, by);
                list.add(deadline);
                addTaskMsg(line, INDENT, deadline, list.size());
            } else if (instruction.equals("event")) {
                String task = inputScanner.hasNextLine() ? inputScanner.nextLine().trim() : "";
                String[] params = task.split("/", 3);
                String description = params[0].trim();
                String start = params[1].trim();
                String end = params[2].trim();

                Event event = new Event(description, start, end);
                list.add(event);
                addTaskMsg(line, INDENT, event, list.size());
            } else if (instruction.equals("mark")) {
                int index = inputScanner.nextInt() - 1;
                if (index >= 0 && index < list.size()) {
                    Task toMark = list.get(index);
                    toMark.markAsDone();
                    System.out.println(line);
                    System.out.println(INDENT + "Nicee! I've marked this task as done:");
                    System.out.println(INDENT + toMark.getString()); // !!
                    System.out.println(line);
                    System.out.println();
                } else {
                    System.out.println("invalid task number, pls try again");
                    System.out.println();
                }
            } else if (instruction.equals("unmark")) {
                int index = inputScanner.nextInt() - 1;
                if (index >= 0 && index < list.size()) {
                    Task toUnMark = list.get(index);
                    toUnMark.unMark();
                    System.out.println(line);
                    System.out.println(INDENT + "OKY, I've marked this task as not done yet:");
                    System.out.println(INDENT + "[" + toUnMark.getStatusIcon() + "]" + toUnMark.description);
                    System.out.println(line);
                    System.out.println();
                } else {
                    System.out.println("invalid task number, pls try again");
                    System.out.println();
                }
            } else {
                Task newTask = new Task(input);
                list.add(newTask);
                System.out.println(line);
                System.out.println(INDENT + "added: " + input);
                System.out.println(line);
                System.out.println();
            }
        }
    }
}
