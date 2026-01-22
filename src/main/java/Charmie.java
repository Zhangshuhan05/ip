import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Charmie {
    public static void main(String[] args) {
        String INDENT = "    ";
        List<String> list = new ArrayList<>();

        System.out.println(INDENT + "____________________________________________________________");
        System.out.println(INDENT + "Hello! I'm Charmie");
        System.out.println(INDENT + "What can I do for you? ;)");
        System.out.println(INDENT + "____________________________________________________________");
        System.out.println();
        Scanner reader = new Scanner(System.in);
        while (true) {
            String input = reader.nextLine();
            if (input.equals("bye")) {
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println(INDENT + "Byee. Hope to see you again soon!");
                System.out.println(INDENT + "____________________________________________________________");
                return;
            } else if (input.equals("list")) {
                System.out.println(INDENT + "____________________________________________________________");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(INDENT + (i+1) + ". " + list.get(i));
                }
                System.out.println(INDENT + "____________________________________________________________");
            } else {
                list.add(input);
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println(INDENT + "added: " + input);
                System.out.println(INDENT + "____________________________________________________________");
                System.out.println();
            }
        }

    }
}
