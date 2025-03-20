package com.task_manager.Controller;

import com.task_manager.Module.Task;
import com.task_manager.Module.TaskRepository;
import com.task_manager.Utils.DataAccessException;
import com.task_manager.Utils.Priority;
import com.task_manager.Utils.Status;
import com.task_manager.View.TaskView;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Логика работы приложения (добавление задач, изменение статуса, напоминания)
 */

public class TaskController {
    private final TaskRepository taskRepository;
    // private final TaskView taskView;

    public TaskController(TaskRepository taskRepository, TaskView taskView) {
        this.taskRepository = taskRepository;
        // this.taskView = taskView;
    }
    // dbg
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        try {
            return taskRepository.getAllTasks();
        } catch (DataAccessException e) {
            System.err.println("Ошибка при получении списка задач: " + e.getMessage());
        }
        return null;
    }

    public void addTask(String title, String description, String category, Priority priority, LocalDateTime due_date) {
        try {
            Task task = new Task(0, title, description, category, priority, due_date);
            taskRepository.addTask(task);
            // updateView();
        } catch (DataAccessException e) {
            System.err.println("Ошибка при добавлении задачи: " + e.getMessage());
        }
    }

    public void editTask(int taskId, Task newTask) {
        try {
            Task oldTask = taskRepository.getTaskById(taskId);
            oldTask.setTitle(newTask.getTitle());
            oldTask.setDescription(newTask.getDescription());
            oldTask.setCategory(newTask.getCategory());
            oldTask.setPriority(newTask.getPriority());
            oldTask.setStatus(newTask.getStatus());
            oldTask.setDue_date(newTask.getDue_date());
            oldTask.setUpdated_at(LocalDateTime.now());

            taskRepository.updateTask(oldTask);
        } catch (DataAccessException e) {
            // dbg
            System.err.println("Ошибка при редактировании задачи: " + e.getMessage());
        }
    }

    public void startTask(int taskId) {
        try {
            Task task = taskRepository.getTaskById(taskId);
            task.setStatus(Status.IN_PROGRESS);
            taskRepository.updateTask(task);
            // updateView();
        } catch (DataAccessException e) {
            System.err.println("Ошибка при начале выполнения задачи: " + e.getMessage());
        }
    }

    public void completeTask(int taskId) {
        try {
            Task task = taskRepository.getTaskById(taskId);
            task.setStatus(Status.COMPLETED);
            taskRepository.updateTask(task);
            // updateView();
        } catch (DataAccessException e) {
            System.err.println("Ошибка при завершении задачи: " + e.getMessage());
        }
    }

    public boolean deleteTask(int taskId) {
        try {
            taskRepository.deleteTask(taskId);
            // updateView();
            return true;
        } catch (DataAccessException e) {
            System.err.println("Ошибка при удалении задачи: " + e.getMessage());
        }
        return false;
    }

}
