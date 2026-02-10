package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Todo {
    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        final String todoFile = "todo.txt";
        BufferedReader reader = null;
        int i = 1;
        String taskName, choice, fileWriteContent = "", fileReadContent;
        mainLoop: while (true) {
            System.out.println("Press 1 to add a task");
            System.out.println("Press 2 to view all tasks");
            System.out.println("Press 3 to mark a task as completed");
            System.out.println("Press 4 to delete a task");

            choice = userInput.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.println("Enter task name:");
                    taskName = userInput.nextLine();
                    fileWriteContent = String.format("Task %d: %s%n", i, taskName);
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(todoFile, true))) {
                        writer.write(fileWriteContent);

                    } catch (IOException e) {
                        System.out.println("Error writing to file: " + e.getMessage());
                    }
                    i++;
                    continue;
                }
                case "2" -> {
                    try {
                        System.out.println("Task overview:");
                        reader = new BufferedReader(new FileReader(todoFile));
                        while ((fileReadContent = reader.readLine()) != null) {
                            System.out.println(fileReadContent);
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                System.out.println("Error closing file: " + e.getMessage());
                            }
                        }
                    }

                    System.out.println("Press b to go back to main menu or q to quit the app");
                    String navigationChoice = userInput.nextLine();
                    if (navigationChoice.equalsIgnoreCase("b")) {
                        continue;
                    } else if (navigationChoice.equalsIgnoreCase("q")) {
                        break mainLoop;
                    }
                }

                case "3" -> {
                    // mark task as completed
                }

                case "4" -> {
                    System.out.println("Enter task number to delete:");
                    String taskToDelete = userInput.nextLine();
                    // Task deletion logic would go here
                    System.out.println("Task deletion feature not yet implemented.");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        userInput.close();

    }
}
