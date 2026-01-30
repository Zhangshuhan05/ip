import java.util.Scanner;

public class Charmie {

    public static void main(String[] args) {
        String INDENT = "    ";
        String line = INDENT + "____________________________________________________________";

        TaskList tasks = new TaskList();
        tasks.loadFromFile();

        Ui charmieUi = new Ui();
        charmieUi.welcomeMsg();

        while (true) {
            try {
                String userInput = charmieUi.readCommand().trim();
                Parser parser = new Parser();
                String instruction = parser.getInstruction(userInput);
                String details = parser.getDetails(userInput);

                switch (instruction) {
                    case "bye":
                        charmieUi.goodbyeMsg();
                        return;
                    case "list":
                        charmieUi.listTasks(tasks);
                        break;
                    case "delete":
                        int index = Integer.parseInt(details) - 1;
                        if (index >= 0 && index < tasks.getSize()) {
                            Task toDel = tasks.getTask(index);
                            tasks.removeTask(index);
                            tasks.saveToDatabase();
                            charmieUi.delTaskMsg(toDel, tasks.getSize());
                        } else {
                            throw new CharmieException("Invalid number, try again.");
                        }
                        break;
                    case "mark":
                        int mIndex = Integer.parseInt(details) - 1;
                        if (mIndex >= 0 && mIndex < tasks.getSize()) {
                            Task marked = tasks.markTask(mIndex);
                            tasks.saveToDatabase();
                            charmieUi.markTaskMsg(marked);
                        } else {
                            throw new CharmieException("Invalid number, try again.");
                        }
                        break;
                    case "unmark":
                        int umIndex = Integer.parseInt(details) - 1;
                        if (umIndex >= 0 && umIndex < tasks.getSize()) {
                            Task unmarked = tasks.unmarkTask(umIndex);
                            tasks.saveToDatabase();
                            charmieUi.unmarkTaskMsg(unmarked);
                        } else {
                            throw new CharmieException("Invalid number, try again.");
                        }
                        break;
                    default:
                        Task task = Parser.parseTask(instruction, details);
                        if (task == null) {
                            throw new CharmieException("OOPS!!! I don't know what that means :-(");
                        }
                        tasks.addTask(task);
                        tasks.saveToDatabase();
                        charmieUi.addTaskMsg(task, tasks.getSize());
                }
            } catch (CharmieException e) {
                charmieUi.showException(e);
            }
    }
}}
