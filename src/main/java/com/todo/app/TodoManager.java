package com.todo.app;

import java.util.*;
import java.util.stream.Collectors;

public class TodoManager {
    private List<Todo> todos;
    
    public TodoManager() {
        this.todos = new ArrayList<>();
    }
    
    public Todo addTodo(String title, String description) {
        Todo todo = new Todo(title, description);
        todos.add(todo);
        return todo;
    }
    
    public boolean removeTodo(int id) {
        return todos.removeIf(todo -> todo.getId() == id);
    }
    
    public Todo getTodoById(int id) {
        return todos.stream()
            .filter(todo -> todo.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public boolean markTodoComplete(int id) {
        Todo todo = getTodoById(id);
        if (todo != null && !todo.isCompleted()) {
            todo.markCompleted();
            return true;
        }
        return false;
    }
    
    public boolean markTodoIncomplete(int id) {
        Todo todo = getTodoById(id);
        if (todo != null && todo.isCompleted()) {
            todo.markIncomplete();
            return true;
        }
        return false;
    }
    
    public List<Todo> getAllTodos() {
        return new ArrayList<>(todos);
    }
    
    public List<Todo> getCompletedTodos() {
        return todos.stream()
            .filter(Todo::isCompleted)
            .collect(Collectors.toList());
    }
    
    public List<Todo> getPendingTodos() {
        return todos.stream()
            .filter(todo -> !todo.isCompleted())
            .collect(Collectors.toList());
    }
    
    public int getTotalCount() {
        return todos.size();
    }
    
    public int getCompletedCount() {
        return (int) todos.stream().filter(Todo::isCompleted).count();
    }
    
    public int getPendingCount() {
        return getTotalCount() - getCompletedCount();
    }
    
    public void clearAllTodos() {
        todos.clear();
        // Reset counter logic would need modification for production
    }
}