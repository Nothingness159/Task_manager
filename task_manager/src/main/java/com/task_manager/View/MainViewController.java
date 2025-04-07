package com.task_manager.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.util.Duration;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.image.Image;
import javafx.geometry.Pos;

public class MainViewController {

    @FXML
    private StackPane allTaskPane;
    @FXML
    private VBox tasksContainerYesterday;
    @FXML
    private VBox tasksContainerToday;
    @FXML
    private VBox tasksContainerTomorrow;
    @FXML
    private StackPane leftPane;
    @FXML
    private Button plusButton;
    @FXML
    private ScrollPane foldersScroll;
    @FXML
    private HBox foldersList;

    private boolean isAllTaskPaneVisible = false;
    private boolean isAddTaskPaneVisible = false;

    private StackPane addTaskPane;
    @FXML
    private ImageView clockIcon1;
    @FXML
    private ImageView clockIcon2;
    @FXML
    private ImageView clockIcon3;

    @FXML
    private Label deadlineLabel1;
    @FXML
    private Label deadlineLabel2;
    @FXML
    private Label deadlineLabel3;

    private final List<List<String>> tasks = new ArrayList<>();

    @FXML
    public void initialize() {
        loadAddTaskPane();
        loadAllTaskPane();
        setupFoldersScroll();
        setupClockIcons();
    }

    private void setupClockIcons() {
        try {
            // Создаем списки для удобства работы с несколькими элементами
            List<ImageView> iconViews = Arrays.asList(clockIcon1, clockIcon2, clockIcon3);
            List<Label> labels = Arrays.asList(deadlineLabel1, deadlineLabel2, deadlineLabel3);

            // Проверяем, что все компоненты инициализированы
            for (int i = 0; i < iconViews.size(); i++) {
                if (iconViews.get(i) == null) {
                    System.err.println("ОШИБКА: clockIcon" + (i + 1) + " не инициализирован");
                }
                if (labels.get(i) == null) {
                    System.err.println("ОШИБКА: deadlineLabel" + (i + 1) + " не инициализирован");
                }
            }

            // Загружаем изображение один раз
            Image clockImage = loadClockImage();
            if (clockImage == null) {
                System.err.println("Не удалось загрузить изображение");
                return;
            }

            // Устанавливаем изображение и обработчики для каждой иконки
            for (int i = 0; i < iconViews.size(); i++) {
                ImageView icon = iconViews.get(i);
                Label label = labels.get(i);

                if (icon != null && label != null) {
                    // Устанавливаем изображение
                    icon.setImage(clockImage);

                    // Настраиваем отображение
                    icon.setFitWidth(16);
                    icon.setFitHeight(16);
                    icon.setPreserveRatio(true);

                    // Для отладки добавляем рамку разных цветов
                    String color = (i == 0) ? "red" : (i == 1) ? "green" : "blue";
                    icon.setStyle("-fx-border-color: " + color + "; -fx-border-width: 1px;");

                    // Настраиваем обработчики событий
                    final Label finalLabel = label;
                    icon.setOnMouseEntered(event -> finalLabel.setVisible(true));
                    icon.setOnMouseExited(event -> finalLabel.setVisible(false));

                    System.out.println("Иконка " + (i + 1) + " настроена успешно");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при настройке иконок: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Метод для загрузки изображения
    private Image loadClockImage() {
        try {
            InputStream stream = getClass().getResourceAsStream("/images/clock-icon.png");
            if (stream != null) {
                Image image = new Image(stream);
                stream.close();
                return image;
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки изображения: " + e.getMessage());
        }

        return null;
    }

    private void setupFoldersScroll() {
        if (foldersList != null && foldersScroll != null) {
            foldersScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            foldersScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            foldersScroll.setPannable(true);

            foldersScroll.setOnScroll(event -> {
                if (event.getDeltaY() != 0) {
                    event.consume();
                    foldersScroll.setHvalue(foldersScroll.getHvalue() - event.getDeltaY() / 500);
                }
            });
        }
    }

    private void loadPane(String fxmlPath, StackPane container) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = loader.load();

            container.getChildren().add(content);
            container.setVisible(false);
            container.setManaged(false);

            leftPane.getChildren().add(container);

        } catch (IOException e) {
            System.err.println("Ошибка загрузки " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAddTaskPane() {
        addTaskPane = new StackPane();
        addTaskPane.getStyleClass().add("add-task-pane");
        loadPane("/fxml/addTask.fxml", addTaskPane);
    }

    private void loadAllTaskPane() {
        if (allTaskPane != null) {
            allTaskPane.setVisible(false);
            allTaskPane.setManaged(false);
        }
    }

    @FXML
    public void showAllTask() {
        toggleAllTaskPane();
    }

    private void toggleAllTaskPane() {
        if (allTaskPane == null)
            return;

        boolean willShow = !isAllTaskPaneVisible;
        allTaskPane.setVisible(willShow);
        allTaskPane.setManaged(willShow);
        isAllTaskPaneVisible = willShow;
    }

    @FXML
    public void showAddTaskDialog() {
        toggleAddTaskPane();
    }

    private void toggleAddTaskPane() {
        if (addTaskPane == null)
            return;

        if (!isAddTaskPaneVisible) {
            // Показать
            addTaskPane.setTranslateY(-300); // начальная позиция сверху
            addTaskPane.setVisible(true);
            addTaskPane.setManaged(true);

            TranslateTransition show = new TranslateTransition(Duration.millis(300), addTaskPane);
            show.setFromY(-300);
            show.setToY(-138);
            show.setToX(62);
            show.play();

            plusButton.setText("▲");
        } else {
            // Скрыть
            TranslateTransition hide = new TranslateTransition(Duration.millis(300), addTaskPane);
            hide.setFromY(0);
            hide.setToY(-300);
            hide.setOnFinished(e -> {
                addTaskPane.setVisible(false);
                addTaskPane.setManaged(false);
            });
            hide.play();

            plusButton.setText("+");
        }

        isAddTaskPaneVisible = !isAddTaskPaneVisible;
    }

    public void addTask(String taskName, String description, LocalDateTime deadline, String difficulty, String folder) {
        List<String> task = List.of(taskName, description, deadline.toString(), difficulty, folder);
        tasks.add(task);
        updateTaskViews();
    }

    private void updateTaskViews() {
        tasksContainerYesterday.getChildren().clear();
        tasksContainerToday.getChildren().clear();
        tasksContainerTomorrow.getChildren().clear();

        for (List<String> task : tasks) {
            HBox taskItem = createTaskItem(task.get(0), task.get(1));
            distributeTaskByDate(taskItem, LocalDateTime.parse(task.get(2)));
        }
    }

    private HBox createTaskItem(String name, String description) {
        HBox taskItem = new HBox(20);
        taskItem.getStyleClass().add("task-container");
        taskItem.setAlignment(Pos.CENTER_LEFT);

        CheckBox checkbox = new CheckBox();
        checkbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                taskItem.getStyleClass().add("task-completed");
            } else {
                taskItem.getStyleClass().remove("task-completed");
            }
        });

        VBox textContainer = new VBox(5);
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add("task-name");
        Label descLabel = new Label(description);
        descLabel.getStyleClass().add("task-description");

        textContainer.getChildren().addAll(nameLabel, descLabel);
        taskItem.getChildren().addAll(checkbox, textContainer);

        return taskItem;
    }

    private void distributeTaskByDate(HBox taskItem, LocalDateTime deadline) {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate taskDate = deadline.toLocalDate();

        if (taskDate.equals(today.minusDays(1))) {
            tasksContainerYesterday.getChildren().add(taskItem);
        } else if (taskDate.equals(today)) {
            tasksContainerToday.getChildren().add(taskItem);
        } else if (taskDate.equals(today.plusDays(1))) {
            tasksContainerTomorrow.getChildren().add(taskItem);
        }
    }
}