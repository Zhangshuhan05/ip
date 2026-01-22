import java.util.Scanner;

public class Charmie {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Charmie");
        System.out.println("What can I do for you? ;)");
        System.out.println("____________________________________________________________");
        System.out.println();
        Scanner reader = new Scanner(System.in);
        while (true) {
            String input = reader.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Byee. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                return;
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(input);
                System.out.println("____________________________________________________________");
                System.out.println();
            }
        }

    }
}
