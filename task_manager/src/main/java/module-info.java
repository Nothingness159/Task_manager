module com.task_manager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.task_manager.View to javafx.fxml;
    // opens com.task_manager.Controller to javafx.fxml;

    opens com.task_manager to javafx.graphics;

    exports com.task_manager.View;
}