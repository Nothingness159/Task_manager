package com.task_manager.Controller;

import com.task_manager.Module.Task;
import com.task_manager.Module.TaskRepository;
import com.task_manager.Module.TaskRepositoryImpl;
import com.task_manager.View.TaskView;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Логика работы приложения (добавление задач, изменение статуса, напоминания)
 */

public class TaskController {

    private final TaskRepository taskRepository;
    //private final TaskView taskView;

    public TaskController(TaskRepository taskRepository, TaskView taskView) {
        this.taskRepository = taskRepository;
        //this.taskView = taskView;
    }

    // dbg
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void addTask(String title, String description, String category, String priority, LocalDateTime due_date) {
        Task task = new Task(0, title, description, category, priority, "pending", due_date, LocalDateTime.now(), LocalDateTime.now());
        taskRepository.addTask(task);
        // updateView();
    }
    // ??? Вот тут возможно как то лучше реализовать
    public void editTask(int taskId, String title, String description, String category, String priority, String status, LocalDateTime due_date, LocalDateTime created_at) {
        Task oldTask = taskRepository.getTaskById(taskId);
        oldTask = new Task(oldTask.getId(), title, description, category, priority, status, due_date, created_at, LocalDateTime.now());
        taskRepository.updateTask(oldTask);
    }

    public void startTask(int taskId) {
        Task task = taskRepository.getTaskById(taskId);
        task.setStatus("in_progress");
        taskRepository.updateTask(task);
        // updateView();
    }

    public void completeTask(int taskId) {
        Task task = taskRepository.getTaskById(taskId);
        task.setStatus("completed");
        taskRepository.updateTask(task);
        // updateView();
    }

    public boolean deleteTask(int taskId) {
        taskRepository.deleteTask(taskId);
        return true;
        // updateView();
    }

}
