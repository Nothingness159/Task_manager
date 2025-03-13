package com.task_manager;

import com.task_manager.Controller.TaskController;
import com.task_manager.Module.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    // Пока что все вспомогательные функции в static

    private static int getUserChoice() {
        while (true) {
            try (Scanner in = new Scanner(System.in)) {
                int var = in.nextInt();
                if (var > 0 && var < 4) {
                    return var;
                } else System.out.println("Вы ввели неверный вариант!");
            } catch (Exception e) {
                System.out.println("Вы ввели неверный вариант!" + e.getMessage());
            }
        }
    }

    private static void viewTasks(TaskController controller) {
        List<Task> taskList = controller.getAllTasks();

        System.out.println("Список задач:");
        for (Task task : taskList) {
            System.out.println(task.getId() + ". "
                             + task.getStatus() + "; "
                             + task.getTitle() + "; "
                             + task.getDescription() + "; "
                             + task.getCategory() + "; "
                             + task.getPriority() + "; "
                             + task.getDue_date() + "; "
                             + task.getCreated_at() + ". "
                             + "\n------------------------ "
            );
        }
    }

    private static void addTask(TaskController controller) {
        // Аттрибуты задачи
        String title;
        String description;
        String category;
        String priority;
        LocalDateTime due_date;

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Введите последовательно данные задачи: ");
            System.out.print("Название: "); title = in.nextLine();
            System.out.print("Описание: "); description = in.nextLine();
            System.out.print("Категория: "); category = in.nextLine();
            System.out.print("Приоритет: "); priority = in.nextLine();
            System.out.print("Дедлайн: "); due_date = localDateTimeParser(in.nextLine());

            controller.addTask(title, description, category, priority, due_date);
        } catch (Exception e) {
            System.out.println("Ошибка ввода!" + e.getMessage());
        }
    }

    private static LocalDateTime localDateTimeParser(String dateString) {
            // 2025-03-23
            String[] separateString;
            separateString = dateString.split("-");

            LocalDateTime dateTime = LocalDateTime.of(0, 1, 1, 0, 0);
            dateTime.plusYears(Integer.parseInt(separateString[0]))
                    .plusMonths(Integer.parseInt(separateString[1]))
                    .plusDays(Integer.parseInt(separateString[2]));

            return dateTime;
        }

    private static void deleteTask(TaskController controller) {
        System.out.println("Введите ID задачи для удаления:");
        int taskId;

        try (Scanner scanner = new Scanner(System.in)) {
            taskId = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Ошибка: Введите корректный числовой ID.");
            return;
        }

        boolean isDeleted = controller.deleteTask(taskId);

        // Provide feedback to the user based on the result
        if (isDeleted) {
            System.out.println("Задача с ID " + taskId + " успешно удалена.");
        } else {
            System.out.println("Ошибка: Задача с ID " + taskId + " не найдена.");
        }
    }

    public static void main(String[] args) {
        TaskController taskController = new TaskController();

        System.out.println();
        System.out.println("TaskManager");

        while(true) {
            System.out.println("""
                    Выберите действие:
                    1. Просмотреть задачи;
                    2. Добавить задачу;
                    3. Удалить задачу;
                    4. Выйти.""");
            int var = getUserChoice();

            switch (var) {
                case 1: viewTasks(taskController); break;
                case 2: addTask(taskController); break;
                case 3: deleteTask(taskController); break;
                case 4: return;
                default: System.out.println("Выбран неверный вариант!");
            }
        }
    }
}
