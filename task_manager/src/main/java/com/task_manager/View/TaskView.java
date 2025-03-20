package com.task_manager.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TaskView {

    @FXML
    private Label titleLabel;

    @FXML
    private void handleAddTask() {
        titleLabel.setText("Задача успешно добавлена!");
    }
}