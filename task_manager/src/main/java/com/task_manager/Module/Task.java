package com.task_manager.Module;

import java.time.LocalDateTime;

/*
 * Структура задачи
 */

public class Task {

    // Поля Базы Данных
    private int id;
    private String title;
    private String description;
    private String category; // Т.е. папка
    private String priority; // low, medium, high
    private String status; // pending, in_progress, completed
    private LocalDateTime due_date; // Дедлайн
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    // Конструкторы
    public Task(int id, String title, String description, String category, String priority, String status, LocalDateTime due_date, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.due_date = due_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }


}
