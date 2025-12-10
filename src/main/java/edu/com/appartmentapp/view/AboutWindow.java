package edu.com.appartmentapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Окно "О программе" - обязательный элемент по заданию
 * Показывает информацию о приложении, авторе и версии
 */
public class AboutWindow {

    public static void show(Stage owner) {
        Stage aboutStage = new Stage();

        // Настройка модальности (блокирует родительское окно)
        aboutStage.initModality(Modality.WINDOW_MODAL);
        aboutStage.initOwner(owner);
        aboutStage.setTitle("О программе");

        // Создание содержимого окна
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f8f9fa;");

        // Заголовок
        Label title = new Label("База данных квартир");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Версия
        Label version = new Label("Версия 1.0");
        version.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // Автор
        Label author = new Label("Автор: Студент");
        author.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

        // Описание программы
        Label description = new Label(
                "Курсовая работа по дисциплине\n" +
                        "«Объектно-ориентированное программирование»\n" +
                        "Вариант 15: Класс, характеризующий квартиру\n\n" +
                        "Программа реализует базу данных квартир\n" +
                        "с использованием кастомного контейнера\n" +
                        "и итератора."
        );
        description.setStyle("-fx-font-size: 12px; -fx-text-fill: #2c3e50;");
        description.setAlignment(Pos.CENTER);
        description.setWrapText(true);

        // Перечень реализованных функций
        Label features = new Label(
                "Реализованные функции:\n" +
                        "• Создание/редактирование/удаление записей\n" +
                        "• Поиск по комнатам и площади\n" +
                        "• Сохранение/загрузка в JSON формате\n" +
                        "• Объединение баз данных\n" +
                        "• Полный графический интерфейс"
        );
        features.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;");
        features.setAlignment(Pos.CENTER_LEFT);
        features.setWrapText(true);

        // Кнопка закрытия
        Button closeButton = new Button("Закрыть");
        closeButton.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-padding: 8 20;"
        );
        closeButton.setOnAction(e -> aboutStage.close());

        // Добавляем все элементы в контейнер
        vbox.getChildren().addAll(
                title,
                version,
                author,
                new javafx.scene.control.Separator(),
                description,
                new javafx.scene.control.Separator(),
                features,
                closeButton
        );

        // Создаем сцену
        Scene scene = new Scene(vbox, 400, 450);

        // Добавляем стили
        try {
            scene.getStylesheets().add(Objects.requireNonNull(
                    AboutWindow.class.getResource("/edu/com/appartmentapp/css/style.css")
            ).toExternalForm());
        } catch (Exception e) {
            System.err.println("Не удалось загрузить стили для AboutWindow: " + e.getMessage());
        }

        // Настройка и отображение окна
        aboutStage.setScene(scene);
        aboutStage.setResizable(false);

        // Центрируем относительно родительского окна
        aboutStage.setX(owner.getX() + owner.getWidth() / 2 - 200);
        aboutStage.setY(owner.getY() + owner.getHeight() / 2 - 225);

        aboutStage.show();
    }
}