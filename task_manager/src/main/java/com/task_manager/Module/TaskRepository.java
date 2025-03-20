package com.task_manager.Module;

import com.task_manager.Utils.DataAccessException;

import java.util.List;

/*
 * Интерфейс для взаимодействия с репозиторием
 */

public interface TaskRepository {
    List<Task> getAllTasks() throws DataAccessException;
    Task getTaskById(int taskId) throws DataAccessException;
    void addTask(Task task) throws DataAccessException;
    void updateTask(Task task) throws DataAccessException;
    void deleteTask(int taskId) throws DataAccessException;
}
