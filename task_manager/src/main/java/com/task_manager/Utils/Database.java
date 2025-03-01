package com.task_manager.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Подключение к базе данных
 */

public class Database {

    private static Connection connection;
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/tasks.db";

    // Метод для получения соединения
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                System.err.println("Ошибка при подключении к БД: " + e.getMessage());
            }
        }
        return connection;
    }
    // Метод для закрытия соединения
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии соединения с БД: " + e.getMessage());
            }
        }
    }
}
