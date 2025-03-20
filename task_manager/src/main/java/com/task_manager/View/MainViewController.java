package com.task_manager.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainViewController {

    @FXML
    private VBox tasksContainerYesterday;

    @FXML
    private VBox tasksContainerToday;

    @FXML
    private VBox tasksContainerTomorrow;

    private List<List<String>> tasks = new ArrayList<>();

    // Метод для добавления задачи
    public void addTask(String taskName, String taskDescription, LocalDateTime deadline, String difficulty,
            String folder) {
        // Создаём заглушку для задачи
        List<String> task = new ArrayList<>();
        task.add(taskName); // Название задачи
        task.add(taskDescription); // Описание задачи
        task.add(deadline.toString()); // Дата выполнения задачи
        task.add(difficulty); // Уровень сложности
        task.add(folder); // Категория задачи

        // Добавляем задачу в список
        tasks.add(task);

        // Обновляем отображение задач
        updateTaskViews();
    }

    @FXML
    private void showAddTaskDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addTask.fxml"));

            Parent root = loader.load();

            AddTaskController addTaskController = loader.getController();
            addTaskController.setMainViewController(this); // передаем MainViewController

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для обновления отображения задач
    private void updateTaskViews() {
        // Очищаем контейнеры для задач
        tasksContainerYesterday.getChildren().clear();
        tasksContainerToday.getChildren().clear();
        tasksContainerTomorrow.getChildren().clear();

        // Перебираем все задачи и добавляем их в соответствующие контейнеры
        for (List<String> task : tasks) {
            HBox taskItem = new HBox(10); // Контейнер для задачи
            CheckBox taskCheckbox = new CheckBox();
            Label taskName = new Label(task.get(0)); // Название задачи
            Label taskDescription = new Label(task.get(1)); // Описание задачи

            // Создание визуального представления задачи
            taskItem.getChildren().addAll(taskCheckbox, taskName, taskDescription);

            // Преобразуем строку с датой выполнения в объект LocalDateTime
            LocalDateTime deadline = LocalDateTime.parse(task.get(2));

            // Распределяем задачи по дням
            if (deadline.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
                tasksContainerYesterday.getChildren().add(taskItem);
            } else if (deadline.toLocalDate().isEqual(LocalDateTime.now().toLocalDate())) {
                tasksContainerToday.getChildren().add(taskItem);
            } else {
                tasksContainerTomorrow.getChildren().add(taskItem);
            }
        }
    }
}
