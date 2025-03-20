package com.task_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaskManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем FXML-файл
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main-view.fxml"));

        // Настраиваем сцену
        Scene scene = new Scene(root, 1500, 800);

        // Подключаем CSS
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());

        // Настраиваем окно
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}