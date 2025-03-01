package com.task_manager.Module;

import com.task_manager.Utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Работа с базой данных (SQLite)
 * Хранилище данных
 */

public class TaskRepository {

    public TaskRepository() {
        createTable();
    }

    private void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    category TEXT,
                    priority TEXT,
                    status TEXT,
                    due_date DATETIME,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    updated_at DATETIME
                );
                """;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getTimestamp("due_date").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при извлечении списка задач: " + e.getMessage());
        }
        return tasks;
    }

    public Task getTaskById(int taskId) {
        Task task = null;
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                task = new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getTimestamp("due_date").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при извлечении задачи по ID: " + e.getMessage());
        }
        return task;
    }

    public void addTask(Task task) {
        String sql = """
                INSERT INTO tasks (title, description, category, priority, status, due_date, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getCategory());
            pstmt.setString(4, task.getPriority());
            pstmt.setString(5, task.getStatus());
            pstmt.setTimestamp(6, Timestamp.valueOf(task.getDue_date()));
            pstmt.setTimestamp(7, Timestamp.valueOf(task.getUpdated_at()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении задачи: " + e.getMessage());
        }
    }

    public void updateTask(Task task) {
        String sql = """
            UPDATE tasks
            SET title = ?,
                description = ?,
                category = ?,
                priority = ?,
                status = ?,
                due_date = ?,
                updated_at = ?
            WHERE id = ?;
            """;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getCategory());
            pstmt.setString(4, task.getPriority());
            pstmt.setString(5, task.getStatus());
            pstmt.setTimestamp(6, Timestamp.valueOf(task.getDue_date()));
            pstmt.setTimestamp(7, Timestamp.valueOf(task.getUpdated_at()));
            pstmt.setInt(8, task.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении задачи: " + e.getMessage());
        }
    }

    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении задачи: " + e.getMessage());
        }
    }

}
