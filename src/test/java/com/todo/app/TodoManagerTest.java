package com.todo.app;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TodoManagerTest {
    private TodoManager todoManager;
    
    @BeforeEach
    void setUp() {
        todoManager = new TodoManager();
    }
    
    @Test
    void testAddTodo() {
        Todo todo = todoManager.addTodo("Test Todo", "Test Description");
        assertNotNull(todo);
        assertEquals("Test Todo", todo.getTitle());
        assertEquals("Test Description", todo.getDescription());
        assertFalse(todo.isCompleted());
        assertEquals(1, todoManager.getTotalCount());
    }
    
    @Test
    void testRemoveTodo() {
        Todo todo = todoManager.addTodo("To Remove", "Remove Me");
        assertEquals(1, todoManager.getTotalCount());
        
        boolean removed = todoManager.removeTodo(todo.getId());
        assertTrue(removed);
        assertEquals(0, todoManager.getTotalCount());
    }
    
    @Test
    void testMarkTodoComplete() {
        Todo todo = todoManager.addTodo("Complete Me", "Task to complete");
        assertFalse(todo.isCompleted());
        
        boolean marked = todoManager.markTodoComplete(todo.getId());
        assertTrue(marked);
        assertTrue(todo.isCompleted());
        assertNotNull(todo.getCompletedAt());
    }
    
    @Test
    void testGetTodoById() {
        todoManager.addTodo("First", "First Task");
        Todo secondTodo = todoManager.addTodo("Second", "Second Task");
        
        Todo found = todoManager.getTodoById(secondTodo.getId());
        assertNotNull(found);
        assertEquals("Second", found.getTitle());
    }
    
    @Test
    void testGetCompletedTodos() {
        Todo todo1 = todoManager.addTodo("Task 1", "Desc 1");
        Todo todo2 = todoManager.addTodo("Task 2", "Desc 2");
        
        todoManager.markTodoComplete(todo1.getId());
        
        assertEquals(1, todoManager.getCompletedCount());
        assertEquals(1, todoManager.getPendingCount());
        assertEquals(2, todoManager.getTotalCount());
    }
    
    @Test
    void testMarkTodoIncomplete() {
        Todo todo = todoManager.addTodo("Task", "Desc");
        todoManager.markTodoComplete(todo.getId());
        assertTrue(todo.isCompleted());
        
        boolean marked = todoManager.markTodoIncomplete(todo.getId());
        assertTrue(marked);
        assertFalse(todo.isCompleted());
        assertNull(todo.getCompletedAt());
    }
    
    @Test
    void testRemoveNonExistentTodo() {
        boolean removed = todoManager.removeTodo(999);
        assertFalse(removed);
        assertEquals(0, todoManager.getTotalCount());
    }
    
    @Test
    void testClearAllTodos() {
        todoManager.addTodo("Task 1", "Desc 1");
        todoManager.addTodo("Task 2", "Desc 2");
        assertEquals(2, todoManager.getTotalCount());
        
        todoManager.clearAllTodos();
        assertEquals(0, todoManager.getTotalCount());
    }
}