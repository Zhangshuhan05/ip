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
                String[] split = userInput.split(" ", 2);
                String instruction = split[0];
                String details = split.length > 1 ? split[1] : "";

                switch (instruction) {
                    case "bye":
                        charmieUi.goodbyeMsg();
                        return;
                    case "list":
                        charmieUi.listTasks(tasks);
                        break;
                    case "todo":
                        if (details.isEmpty()) {
                            throw new CharmieException("OOPS!!! The description of a todo cannot be empty :(");
                        }
                        ToDo toDo = new ToDo(details);
                        tasks.addTask(toDo);
                        tasks.saveToDatabase();
                        charmieUi.addTaskMsg(toDo, tasks.getSize());
                        break;
                    case "deadline":
                        String[] param = details.split("/by", 2);
                        if (param.length < 2 || param[0].trim().isEmpty() || param[1].trim().isEmpty()) {
                            throw new CharmieException("OOPS!!! I need more details for your deadline :(");
                        }
                        String descriptionD = param[0].trim();
                        String by = param[1].trim();

                        Deadline deadline = new Deadline(descriptionD, by);
                        tasks.addTask(deadline);
                        tasks.saveToDatabase();
                        charmieUi.addTaskMsg(deadline, tasks.getSize());
                        break;
                    case "event":
                        String[] params = details.split("/", 3);
                        if (params.length < 3 || params[0].trim().isEmpty() || params[1].trim().isEmpty() || params[2].trim().isEmpty()) {
                            throw new CharmieException("OOPS!!! I need more details for your event :(");
                        }
                        String descriptionE = params[0].trim();
                        String start = params[1].trim().replace("from", "").trim();
                        String end = params[2].trim().replace("to", "").trim();

                        Event event = new Event(descriptionE, start, end);
                        tasks.addTask(event);
                        tasks.saveToDatabase();
                        charmieUi.addTaskMsg(event, tasks.getSize());
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
                        throw new CharmieException("OOPS!!! I don't know what that means :-(, can you be more specific?");
                }
            } catch (CharmieException e) {
                charmieUi.showException(e);
            }
    }
}}
