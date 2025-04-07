package com.task_manager.View;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    private MainViewController mainViewController;

    @FXML
    public void initialize() {
        if (difficultyComboBox != null) {
            difficultyComboBox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
        }

        if (folderComboBox != null) {
            folderComboBox.setItems(FXCollections.observableArrayList("Work", "Personal", "Urgent"));
        }

        // Установим текущую дату по умолчанию
        if (deadlineDatePicker != null) {
            deadlineDatePicker.setValue(LocalDate.now());
        }

        // Подсказка по формату времени
        if (deadlineTimeField != null) {
            deadlineTimeField.setPromptText("HH:mm");
        }
    }

    public void setMainViewController(MainViewController controller) {
        this.mainViewController = controller;
    }

    @FXML
    private void addTask() {
        if (!validateFields())
            return;

        try {
            LocalDateTime deadline = parseDeadline();

            if (mainViewController != null) {
                mainViewController.addTask(
                        taskNameField.getText().trim(),
                        taskDescriptionField.getText().trim(),
                        deadline,
                        difficultyComboBox.getValue(),
                        folderComboBox.getValue());

                mainViewController.showAddTaskDialog();
                clearFields();
            }

        } catch (DateTimeParseException e) {
            showAlert("Invalid Time Format", "Please enter time in HH:mm format.");
        }
    }

    @FXML
    private void cancelTask() {
        clearFields();

        if (mainViewController != null) {
            mainViewController.showAddTaskDialog();
        }
    }

    private boolean validateFields() {
        if (taskNameField.getText().isBlank()
                || deadlineDatePicker.getValue() == null
                || deadlineTimeField.getText().isBlank()) {
            showAlert("Missing Information", "Please fill in the task name, date, and time.");
            return false;
        }
        return true;
    }

    private LocalDateTime parseDeadline() throws DateTimeParseException {
        LocalDate date = deadlineDatePicker.getValue();
        String timeText = deadlineTimeField.getText().trim();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(timeText, timeFormatter);

        return LocalDateTime.of(date, time);
    }

    private void clearFields() {
        taskNameField.clear();
        taskDescriptionField.clear();
        deadlineDatePicker.setValue(LocalDate.now());
        deadlineTimeField.clear();
        difficultyComboBox.getSelectionModel().clearSelection();
        folderComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
