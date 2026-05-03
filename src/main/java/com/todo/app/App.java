package com.todo.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static TodoManager todoManager = new TodoManager();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        logger.info("Starting Todo List Application");
        System.out.println("===================================");
        System.out.println("   Welcome to Todo List Manager");
        System.out.println("===================================\n");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addTodo();
                    break;
                case 2:
                    viewAllTodos();
                    break;
                case 3:
                    markTodoComplete();
                    break;
                case 4:
                    removeTodo();
                    break;
                case 5:
                    viewStatistics();
                    break;
                case 6:
                    viewPendingTodos();
                    break;
                case 7:
                    viewCompletedTodos();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nGoodbye! Have a productive day!");
                    logger.info("Application shutdown");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n=== Todo List Menu ===");
        System.out.println("1. Add Todo");
        System.out.println("2. View All Todos");
        System.out.println("3. Mark Todo Complete");
        System.out.println("4. Remove Todo");
        System.out.println("5. View Statistics");
        System.out.println("6. View Pending Todos");
        System.out.println("7. View Completed Todos");
        System.out.println("8. Exit");
        System.out.print("Choice: ");
    }
    
    private static void addTodo() {
        System.out.println("\n--- Add New Todo ---");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        Todo newTodo = todoManager.addTodo(title, description);
        System.out.println("✓ Todo added successfully with ID: " + newTodo.getId());
        logger.info("Added new todo: {}", title);
    }
    
    private static void viewAllTodos() {
        System.out.println("\n--- All Todos ---");
        if (todoManager.getTotalCount() == 0) {
            System.out.println("No todos found. Add some todos first!");
            return;
        }
        
        for (Todo todo : todoManager.getAllTodos()) {
            System.out.println("\n" + todo);
            System.out.println("-".repeat(50));
        }
    }
    
    private static void markTodoComplete() {
        System.out.println("\n--- Mark Todo Complete ---");
        viewAllTodos();
        if (todoManager.getTotalCount() == 0) return;
        
        int id = getIntInput("Enter Todo ID to mark as complete: ");
        if (todoManager.markTodoComplete(id)) {
            System.out.println("✓ Todo #" + id + " marked as complete!");
            logger.info("Marked todo {} as complete", id);
        } else {
            System.out.println("✗ Todo not found or already completed!");
        }
    }
    
    private static void removeTodo() {
        System.out.println("\n--- Remove Todo ---");
        viewAllTodos();
        if (todoManager.getTotalCount() == 0) return;
        
        int id = getIntInput("Enter Todo ID to remove: ");
        if (todoManager.removeTodo(id)) {
            System.out.println("✓ Todo #" + id + " removed successfully!");
            logger.info("Removed todo {}", id);
        } else {
            System.out.println("✗ Todo not found!");
        }
    }
    
    private static void viewStatistics() {
        System.out.println("\n--- Todo Statistics ---");
        System.out.printf("Total Todos: %d%n", todoManager.getTotalCount());
        System.out.printf("Completed: %d (%.1f%%)%n", 
            todoManager.getCompletedCount(),
            todoManager.getTotalCount() > 0 ? 
                (todoManager.getCompletedCount() * 100.0 / todoManager.getTotalCount()) : 0);
        System.out.printf("Pending: %d (%.1f%%)%n", 
            todoManager.getPendingCount(),
            todoManager.getTotalCount() > 0 ? 
                (todoManager.getPendingCount() * 100.0 / todoManager.getTotalCount()) : 0);
    }
    
    private static void viewPendingTodos() {
        System.out.println("\n--- Pending Todos ---");
        if (todoManager.getPendingCount() == 0) {
            System.out.println("No pending todos. Great job!");
            return;
        }
        
        for (Todo todo : todoManager.getPendingTodos()) {
            System.out.println("\n" + todo);
        }
    }
    
    private static void viewCompletedTodos() {
        System.out.println("\n--- Completed Todos ---");
        if (todoManager.getCompletedCount() == 0) {
            System.out.println("No completed todos yet. Keep working!");
            return;
        }
        
        for (Todo todo : todoManager.getCompletedTodos()) {
            System.out.println("\n" + todo);
        }
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }
}