package com.java.taskmanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * TaskManager CLI application
 * 
 * Features:
 * 1. Add tasks with title, priority, and deadline
 * 2. View all tasks
 * 3. Filter tasks by priority
 * 4. Sort tasks by deadline
 * 5. Group tasks by due date
 */
public class TaskManager {

    // Scanner for reading user input from the console
    private static final Scanner scanner = new Scanner(System.in);

    // List to store all tasks added by the user
    private static final List<Task> tasks = new ArrayList<>();

    /**
     * Main method: Entry point of the CLI application.
     * Displays menu options and handles user choices in a loop.
     */
    public static void main(String[] args) {
        while (true) {
            // Display menu options
            System.out.println(
                "\n1. Add Task\n2. View All\n3. Filter by Priority\n4. Sort by Deadline\n5. Group by Date\n6. Exit");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                // Parse user input to integer
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                // Handle invalid input gracefully
                System.out.println("❌ Invalid choice. Please enter a number between 1 and 6.");
                continue;
            }

            // Execute corresponding action based on user choice
            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> filterByPriority();
                case 4 -> sortByDeadline();
                case 5 -> groupByDate();
                case 6 -> {
                    System.out.println("👋 Exiting Task Manager. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("❌ Invalid choice. Please select a valid option.");
            }
        }
    }

    /**
     * Adds a new task to the task list.
     * Expects input in the format: title priority deadline
     */
    private static void addTask() {
        System.out.print("Enter task details (title priority deadline): ");
        String input = scanner.nextLine().trim();

        // Split input into tokens
        String[] tokens = input.split(" ");
        if (tokens.length < 3) {
            System.out.println("❌ Please enter at least 3 values: title priority deadline");
            return;
        }

        try {
            // Extract priority and deadline from the last two tokens
            int priority = Integer.parseInt(tokens[tokens.length - 2]);
            LocalDate deadline = LocalDate.parse(tokens[tokens.length - 1]);

            // Combine remaining tokens as the task title
            String title = String.join(" ", Arrays.copyOf(tokens, tokens.length - 2));

            // Add task to the list
            tasks.add(new Task(title, priority, deadline));
            System.out.println("✅ Task added: " + title);
        } catch (NumberFormatException e) {
            System.out.println("❌ Priority must be a number (1, 2, or 3).");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Displays all tasks in the task list.
     * If no tasks exist, shows a message.
     */
    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    /**
     * Filters and displays tasks based on user-specified priority.
     */
    private static void filterByPriority() {
        System.out.print("Enter priority to filter (1=High, 2=Medium, 3=Low): ");
        int priority = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline
        tasks.stream()
            .filter(task -> task.getPriority() == priority)
            .forEach(System.out::println);
    }

    /**
     * Sorts tasks by deadline in ascending order and displays them.
     */
    private static void sortByDeadline() {
        tasks.stream()
            .sorted((t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline()))
            .forEach(System.out::println);
    }

    /**
     * Groups tasks by their deadline date and displays each group.
     */
    private static void groupByDate() {
        Map<LocalDate, List<Task>> grouped = tasks.stream()
            .collect(Collectors.groupingBy(Task::getDeadline));

        grouped.forEach((date, taskList) -> {
            System.out.println("\nDue: " + date);
            taskList.forEach(System.out::println);
        });
    }
}
