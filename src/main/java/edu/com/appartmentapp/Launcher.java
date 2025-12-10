package edu.com.appartmentapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import edu.com.appartmentapp.controller.MainController;

import java.util.Objects;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загружаем основное окно из FXML
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/com/appartmentapp/view/main.fxml")
        );
        Parent mainContent = loader.load();

        // Получаем контроллер и передаем ему stage
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        // Создаем главное меню (без вызовов методов контроллера)
        MenuBar menuBar = createMenuBar(primaryStage);

        // Создаем панель инструментов (без вызовов методов контроллера)
        ToolBar toolBar = createToolBar();

        // Создаем строку состояния
        Label statusBar = new Label("Готов к работе");
        statusBar.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 5; -fx-font-size: 12;");

        // Создаем основной layout
        BorderPane borderPane = new BorderPane();
        VBox topBox = new VBox();
        topBox.getChildren().addAll(menuBar, toolBar);
        borderPane.setTop(topBox);
        borderPane.setCenter(mainContent);
        borderPane.setBottom(statusBar);

        // Создаем сцену
        Scene scene = new Scene(borderPane, 1000, 700);

        // Добавляем стили
        try {
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/edu/com/appartmentapp/css/style.css")
            ).toExternalForm());
        } catch (Exception e) {
            System.err.println("Не удалось загрузить стили: " + e.getMessage());
        }

        // Настраиваем главное окно
        primaryStage.setTitle("База данных квартир - Вариант 15");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        // Меню Файл
        Menu fileMenu = new Menu("Файл");

        MenuItem exitItem = new MenuItem("Выход");
        exitItem.setOnAction(e -> stage.close());

        fileMenu.getItems().addAll(exitItem);

        // Меню Справка
        Menu helpMenu = new Menu("Справка");

        MenuItem aboutItem = new MenuItem("О программе");
        aboutItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("О программе");
            alert.setHeaderText("База данных квартир");
            alert.setContentText("Курсовая работа по ООП\nВариант 15");
            alert.showAndWait();
        });

        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        return menuBar;
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        return toolBar;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}