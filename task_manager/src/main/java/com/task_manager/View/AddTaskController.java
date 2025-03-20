package com.task_manager.View;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddTaskController {

    @FXML
    private TextField taskNameField;
    @FXML
    private TextField taskDescriptionField;
    @FXML
    private ComboBox<String> difficultyComboBox;
    @FXML
    private ComboBox<String> folderComboBox;
    @FXML
    private DatePicker deadlineDatePicker;
    @FXML
    private TextField deadlineTimeField;
    @FXML
    private Button addTaskButton;

    private MainViewController mainViewController; // Ссылка на контроллер главного окна

    @FXML
    public void initialize() {
        difficultyComboBox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
        folderComboBox.setItems(FXCollections.observableArrayList("Work", "Personal", "Urgent"));
    }

    // Устанавливаем ссылку на контроллер главного окна
    public void setMainViewController(MainViewController controller) {
        this.mainViewController = controller;
    }

    // Метод для добавления задачи
    @FXML
    private void addTask() {
        String taskName = taskNameField.getText();
        String taskDescription = taskDescriptionField.getText();
        String difficulty = difficultyComboBox.getValue();
        String folder = folderComboBox.getValue();
        LocalDate selectedDate = deadlineDatePicker.getValue();
        String timeString = deadlineTimeField.getText();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            if (taskName != null && !taskName.isEmpty() && selectedDate != null && timeString != null
                    && !timeString.isEmpty()) {
                LocalDateTime deadline = LocalDateTime.of(selectedDate,
                        LocalDateTime.parse(timeString, timeFormatter).toLocalTime());

                if (mainViewController != null) {
                    mainViewController.addTask(taskName, taskDescription, deadline, difficulty, folder);
                }

                // Закрытие окна добавления задачи
                Stage stage = (Stage) addTaskButton.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Please fill all fields correctly.");
            }
        } catch (Exception e) {
            System.out.println("Invalid time format. Please enter time in HH:mm format.");
        }
    }
}
