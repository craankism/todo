package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Todo {
    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        String taskName, choice, fileContent = "";

        System.out.println("Press 1 to add a task");
        System.out.println("Press 2 to view all tasks");
        System.out.println("Press 3 to mark a task as completed");
        System.out.println("Press 4 to delete a task");

        choice = userInput.nextLine();

        switch (choice) {
            case "1" -> {
                System.out.println("Enter task name:");
                taskName = userInput.nextLine();
                fileContent = String.format("Task 1: %s%n", taskName);
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("todo.txt", true));
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }
}
