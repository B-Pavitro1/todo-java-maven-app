package com.todo.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Todo {
    private static int counter = 1;
    private int id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    
    public Todo(String title, String description) {
        this.id = counter++;
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.completedAt = null;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isCompleted() { return completed; }
    
    public void markCompleted() {
        this.completed = true;
        this.completedAt = LocalDateTime.now();
    }
    
    public void markIncomplete() {
        this.completed = false;
        this.completedAt = null;
    }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    
    @Override
    public String toString() {
        String status = completed ? "✓" : "✗";
        String dateStr = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String completedStr = completed && completedAt != null ? 
            " (completed at: " + completedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ")" : "";
        
        return String.format("[%s] #%d - %s\n    %s\n    Created: %s%s", 
            status, id, title, description, dateStr, completedStr);
    }
}