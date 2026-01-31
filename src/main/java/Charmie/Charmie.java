package Charmie;

import Charmie.task.Task;
import Charmie.task.TaskList;


import java.io.IOException;

public class Charmie {

    public static void main(String[] args) {
        String INDENT = "    ";
        String line = INDENT + "____________________________________________________________";

        Storage storage = new Storage("./data/charmie.txt");
        TaskList tasks;

        try {
            tasks = storage.loadFromFile();
        } catch (IOException e) {
            tasks = new TaskList();
        }

        Ui charmieUi = new Ui();
        charmieUi.welcomeMsg();

        while (true) {
            try {
                String userInput = charmieUi.readCommand().trim();
                String instruction = Parser.getInstruction(userInput);
                String details = Parser.getDetails(userInput);

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
                            try {
                                storage.saveToFile(tasks);
                            } catch (IOException e) {
                                charmieUi.showException(
                                        new CharmieException("OOPS!!! I had trouble saving your tasks 😢")
                                );
                            }
                            charmieUi.delTaskMsg(toDel, tasks.getSize());
                        } else {
                            throw new CharmieException("Invalid number, try again.");
                        }
                        break;
                    case "mark":
                        int mIndex = Integer.parseInt(details) - 1;
                        if (mIndex >= 0 && mIndex < tasks.getSize()) {
                            Task marked = tasks.markTask(mIndex);
                            try {
                                storage.saveToFile(tasks);
                            } catch (IOException e) {
                                charmieUi.showException(
                                        new CharmieException("OOPS!!! I had trouble saving your tasks 😢")
                                );
                            }
                            charmieUi.markTaskMsg(marked);
                        } else {
                            throw new CharmieException("Invalid number, try again.");
                        }
                        break;
                    case "unmark":
                        int umIndex = Integer.parseInt(details) - 1;
                        if (umIndex >= 0 && umIndex < tasks.getSize()) {
                            Task unmarked = tasks.unmarkTask(umIndex);
                            try {
                                storage.saveToFile(tasks);
                            } catch (IOException e) {
                                charmieUi.showException(
                                        new CharmieException("OOPS!!! I had trouble saving your tasks 😢")
                                );
                            }
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
                        try {
                            storage.saveToFile(tasks);
                        } catch (IOException e) {
                            charmieUi.showException(
                                    new CharmieException("OOPS!!! I had trouble saving your tasks 😢")
                            );
                        }
                        charmieUi.addTaskMsg(task, tasks.getSize());
                }
            } catch (CharmieException e) {
                charmieUi.showException(e);
            }
    }
}}
