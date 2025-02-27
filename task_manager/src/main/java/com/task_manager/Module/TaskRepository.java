package com.task_manager.Module;

import com.task_manager.Utils.Database;

import java.sql.Connection;
import java.util.List;

/*
 * Работа с базой данных (SQLite)
 * Хранилище данных
 */

public class TaskRepository {

    private Connection connection;

    public TaskRepository() {
        this.connection = Database.getConnection();
    }

    public List<Task> getAllTasks() { /* SQL-запросы */ }
    public Task getTaskById(int taskId) { /* SQL-запрос */ }
    public void addTask(Task task) { /* SQL INSERT */ }
    public void updateTask(Task task) { /* SQL UPDATE */ }
    public void deleteTask(int taskId) { /* SQL DELETE */ }

}
