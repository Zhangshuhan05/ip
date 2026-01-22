import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Charmie {
    public static void main(String[] args) {
        String INDENT = "    ";
        List<Task> list = new ArrayList<>();

        System.out.println(INDENT + "____________________________________________________________");
        System.out.println(INDENT + "Hello! I'm Charmie");
        System.out.println(INDENT + "What can I do for you? ;)");
        System.out.println(INDENT + "____________________________________________________________");
        System.out.println();
        Scanner reader = new Scanner(System.in);
        while (true) {
            String input = reader.nextLine();
            Scanner inputScanner = new Scanner(input);
            String instruction = inputScanner.next();
            if (instruction.equals("bye")) {
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println(INDENT + "Byee. Hope to see you again soon!");
                System.out.println(INDENT + "____________________________________________________________");
                return;
            } else if (instruction.equals("list")) {
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println(INDENT + "Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    Task task = list.get(i);
                    System.out.println(INDENT + (i+1) + ". [" + task.getStatusIcon() + "] " + task.description);
                }
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println();
            } else if (instruction.equals("mark")) {
                int index = inputScanner.nextInt() - 1;
                if (index >= 0 && index < list.size()) {
                    Task toMark = list.get(index);
                    toMark.markAsDone();
                    System.out.println(INDENT + "____________________________________________________________");
                    System.out.println(INDENT + "Nicee! I've marked this task as done:");
                    System.out.println(INDENT + "[" + toMark.getStatusIcon() + "]" + toMark.description);
                    System.out.println(INDENT + "____________________________________________________________");
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
                    System.out.println(INDENT + "____________________________________________________________");
                    System.out.println(INDENT + "OKY, I've marked this task as not done yet:");
                    System.out.println(INDENT + "[" + toUnMark.getStatusIcon() + "]" + toUnMark.description);
                    System.out.println(INDENT + "____________________________________________________________");
                    System.out.println();
                } else {
                    System.out.println("invalid task number, pls try again");
                    System.out.println();
                }
            } else {
                Task newTask = new Task(input);
                list.add(newTask);
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println(INDENT + "added: " + input);
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println();
            }
        }

    }
}
