package com.task_manager.Module;

import com.task_manager.Utils.DataAccessException;
import com.task_manager.Utils.Database;
import com.task_manager.Utils.Priority;
import com.task_manager.Utils.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Работа с базой данных (SQLite)
 * Хранилище данных
 */

public class TaskRepositoryImpl implements TaskRepository {
    private static final TaskRepositoryImpl INSTANCE = new TaskRepositoryImpl();

    public TaskRepositoryImpl() {
        createTable();
    }

    public static TaskRepositoryImpl getInstance() {
        return INSTANCE;
    }

    private void createTable() throws DataAccessException {
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    category TEXT,
                    priority TEXT CHECK (priority IN ('HIGH', 'MEDIUM', 'LOW')),
                    status TEXT CHECK (status IN ('PENDING', 'IN_PROGRESS', 'COMPLETED')),
                    due_date DATETIME NOT NULL,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
                );
                """;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при создании таблицы", e);
        }
    }

    public List<Task> getAllTasks() throws DataAccessException {
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
                        Priority.valueOf(rs.getString("priority")),
                        Status.valueOf(rs.getString("status")),
                        rs.getTimestamp("due_date").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при извлечении списка задач", e);
        }
        return tasks;
    }

    public Task getTaskById(int taskId) throws DataAccessException {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        Priority.valueOf(rs.getString("priority")),
                        Status.valueOf(rs.getString("status")),
                        rs.getTimestamp("due_date").toLocalDateTime(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при извлечении задачи по ID", e);
        }
        return null;
    }

    public void addTask(Task task) throws DataAccessException {
        String sql = """
                INSERT INTO tasks (title, description, category, priority, status, due_date)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task.getTitle());
            pstmt.setString(2, task.getDescription());
            pstmt.setString(3, task.getCategory());
            pstmt.setString(4, task.getPriority().toString());
            pstmt.setString(5, task.getStatus().toString());
            pstmt.setTimestamp(6, Timestamp.valueOf(task.getDue_date()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при добавлении задачи", e);
        }
    }

    public void updateTask(Task task) throws DataAccessException {
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
            pstmt.setString(4, task.getPriority().toString());
            pstmt.setString(5, task.getStatus().toString());
            pstmt.setTimestamp(6, Timestamp.valueOf(task.getDue_date()));
            pstmt.setTimestamp(7, Timestamp.valueOf(task.getUpdated_at()));
            pstmt.setInt(8, task.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при обновлении задачи", e);
        }
    }

    public void deleteTask(int taskId) throws DataAccessException {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Ошибка при удалении задачи", e);
        }
    }
}
