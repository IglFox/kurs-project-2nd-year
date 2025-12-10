package edu.com.appartmentapp.controller;

import edu.com.appartmentapp.model.Apartment;
import edu.com.appartmentapp.model.CustomContainer;
import edu.com.appartmentapp.model.Database;
import edu.com.appartmentapp.view.AboutWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Контроллер для главного окна приложения
 * Связывает модель (Database) с представлением (FXML)
 */
public class MainController {
    // Элементы таблицы
    @FXML private TableView<Apartment> tableView;
    @FXML private TableColumn<Apartment, Integer> idColumn;
    @FXML private TableColumn<Apartment, Integer> roomsColumn;
    @FXML private TableColumn<Apartment, Double> areaColumn;
    @FXML private TableColumn<Apartment, Integer> floorColumn;
    @FXML private TableColumn<Apartment, String> addressColumn;
    @FXML private TableColumn<Apartment, Double> priceColumn;
    @FXML private TableColumn<Apartment, String> balconyColumn;

    // Поля для ввода данных
    @FXML private TextField roomsField;
    @FXML private TextField areaField;
    @FXML private TextField floorField;
    @FXML private TextField addressField;
    @FXML private TextField priceField;
    @FXML private CheckBox balconyCheckBox;

    // Элементы поиска
    @FXML private TextField searchRoomsField;
    @FXML private TextField searchMinAreaField;
    @FXML private TextField searchMaxAreaField;

    // Статусная строка
    @FXML private Label statusLabel;

    private Database database;              // Модель данных
    private ObservableList<Apartment> observableList;  // Данные для таблицы
    private Stage primaryStage;            // Главное окно

    /**
     * Инициализация контроллера (вызывается автоматически JavaFX)
     */
    @FXML
    public void initialize() {
        database = new Database();
        observableList = FXCollections.observableArrayList();

        // Настройка связей колонок таблицы с полями объекта Apartment
        setupTableColumns();

        // Загружаем тестовые данные
        addSampleData();

        updateStatus("Готово. Загружено " + database.getSize() + " квартир");
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Кастомная фабрика для колонки с балконом
        balconyColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().isHasBalcony() ? "Да" : "Нет"
                ));

        tableView.setItems(observableList);
    }

    /**
     * Добавление тестовых данных для демонстрации
     */
    private void addSampleData() {
        database.addApartment(new Apartment(0, 2, 45.5, 3, "ул. Ленина, 10", 3500000, true));
        database.addApartment(new Apartment(0, 3, 72.0, 7, "пр. Мира, 25", 5200000, true));
        database.addApartment(new Apartment(0, 1, 32.0, 1, "ул. Пушкина, 5", 2800000, false));
        database.addApartment(new Apartment(0, 4, 95.0, 12, "ул. Советская, 15", 7800000, true));
        database.addApartment(new Apartment(0, 2, 48.0, 5, "ул. Гагарина, 8", 3600000, false));

        refreshTable();
    }

    /**
     * Обновление данных в таблице
     */
    private void refreshTable() {
        observableList.clear();
        CustomContainer<Apartment> apartments = database.getAllApartments();
        edu.com.appartmentapp.model.Iterator<Apartment> it = apartments.iterator();
        while (it.hasNext()) {
            observableList.add(it.next());
        }
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    // ========== Обработчики событий ==========

    /**
     * Добавление новой квартиры
     */
    @FXML
    private void handleAddApartment() {
        try {
            Apartment apartment = createApartmentFromFields();
            database.addApartment(apartment);

            refreshTable();
            clearFields();
            updateStatus("Квартира добавлена");

        } catch (NumberFormatException e) {
            showAlert("Ошибка ввода", "Проверьте правильность введенных данных");
        }
    }

    /**
     * Удаление выбранной квартиры
     */
    @FXML
    private void handleDeleteApartment() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            database.removeApartment(selectedIndex);
            refreshTable();
            updateStatus("Квартира удалена");
        } else {
            showAlert("Внимание", "Выберите квартиру для удаления");
        }
    }

    /**
     * Поиск по количеству комнат
     */
    @FXML
    private void handleSearchByRooms() {
        try {
            int rooms = Integer.parseInt(searchRoomsField.getText());
            CustomContainer<Apartment> result = database.searchByRooms(rooms);

            showSearchResult(result);
            updateStatus("Найдено " + result.size() + " квартир с " + rooms + " комнатами");
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректное число комнат");
        }
    }

    /**
     * Поиск по площади
     */
    @FXML
    private void handleSearchByArea() {
        try {
            double minArea = Double.parseDouble(searchMinAreaField.getText());
            double maxArea = Double.parseDouble(searchMaxAreaField.getText());

            if (minArea > maxArea) {
                showAlert("Ошибка", "Минимальная площадь должна быть меньше максимальной");
                return;
            }

            CustomContainer<Apartment> result = database.searchByArea(minArea, maxArea);
            showSearchResult(result);
            updateStatus("Найдено " + result.size() + " квартир в диапазоне площади");
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректные значения площади");
        }
    }

    /**
     * Показать все квартиры
     */
    @FXML
    public void handleShowAll() {
        refreshTable();
        updateStatus("Показаны все квартиры");
    }

    /**
     * Сохранение в файл
     */
    @FXML
    private void handleSaveToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить базу данных");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON файлы", "*.json"));

        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                database.saveToFile(file.getAbsolutePath());
                updateStatus("База данных сохранена: " + file.getName());
            } catch (IOException e) {
                showAlert("Ошибка сохранения", e.getMessage());
            }
        }
    }

    /**
     * Загрузка из файла
     */
    @FXML
    private void handleLoadFromFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить базу данных");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON файлы", "*.json"));

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                database.loadFromFile(file.getAbsolutePath());
                refreshTable();
                updateStatus("База данных загружена: " + file.getName());
            } catch (IOException e) {
                showAlert("Ошибка загрузки", e.getMessage());
            }
        }
    }

    /**
     * Объединение с другой базой
     */
    @FXML
    private void handleMergeDatabase() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Объединить с другой базой");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON файлы", "*.json"));

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                Database mergeDb = new Database();
                mergeDb.loadFromFile(file.getAbsolutePath());
                database.mergeDatabase(mergeDb);
                refreshTable();
                updateStatus("Базы данных объединены");
            } catch (IOException e) {
                showAlert("Ошибка объединения", e.getMessage());
            }
        }
    }

    /**
     * Редактирование выбранной квартиры
     */
    @FXML
    private void handleEditApartment() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Apartment selected = tableView.getSelectionModel().getSelectedItem();

            // Отображаем диалог редактирования
            Dialog<Apartment> dialog = createEditDialog(selected);

            dialog.showAndWait().ifPresent(updatedApartment -> {
                if (updatedApartment != null) {
                    database.updateApartment(selectedIndex, updatedApartment);
                    refreshTable();
                    updateStatus("Квартира отредактирована");
                }
            });
        } else {
            showAlert("Внимание", "Выберите квартиру для редактирования");
        }
    }

    /**
     * Показать окно "О программе"
     */
    @FXML
    private void handleShowAbout() {
        AboutWindow.show(primaryStage);
    }

    // ========== Вспомогательные методы ==========

    private Apartment createApartmentFromFields() throws NumberFormatException {
        int rooms = Integer.parseInt(roomsField.getText());
        double area = Double.parseDouble(areaField.getText());
        int floor = Integer.parseInt(floorField.getText());
        String address = addressField.getText();
        double price = Double.parseDouble(priceField.getText());
        boolean hasBalcony = balconyCheckBox.isSelected();

        return new Apartment(0, rooms, area, floor, address, price, hasBalcony);
    }

    private void showSearchResult(CustomContainer<Apartment> result) {
        observableList.clear();
        edu.com.appartmentapp.model.Iterator<Apartment> it = result.iterator();
        while (it.hasNext()) {
            observableList.add(it.next());
        }
    }

    private Dialog<Apartment> createEditDialog(Apartment apartment) {
        Dialog<Apartment> dialog = new Dialog<>();
        dialog.setTitle("Редактирование квартиры");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        // Поля для редактирования
        TextField roomsField = new TextField(String.valueOf(apartment.getNumberOfRooms()));
        TextField areaField = new TextField(String.valueOf(apartment.getArea()));
        TextField floorField = new TextField(String.valueOf(apartment.getFloor()));
        TextField addressField = new TextField(apartment.getAddress());
        TextField priceField = new TextField(String.valueOf(apartment.getPrice()));
        CheckBox balconyCheckBox = new CheckBox("Балкон");
        balconyCheckBox.setSelected(apartment.isHasBalcony());

        grid.add(new Label("Комнат:"), 0, 0);
        grid.add(roomsField, 1, 0);
        grid.add(new Label("Площадь (м²):"), 0, 1);
        grid.add(areaField, 1, 1);
        grid.add(new Label("Этаж:"), 0, 2);
        grid.add(floorField, 1, 2);
        grid.add(new Label("Адрес:"), 0, 3);
        grid.add(addressField, 1, 3);
        grid.add(new Label("Цена:"), 0, 4);
        grid.add(priceField, 1, 4);
        grid.add(balconyCheckBox, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    return new Apartment(
                            apartment.getId(),
                            Integer.parseInt(roomsField.getText()),
                            Double.parseDouble(areaField.getText()),
                            Integer.parseInt(floorField.getText()),
                            addressField.getText(),
                            Double.parseDouble(priceField.getText()),
                            balconyCheckBox.isSelected()
                    );
                } catch (NumberFormatException e) {
                    showAlert("Ошибка", "Проверьте правильность введенных данных");
                    return null;
                }
            }
            return null;
        });

        return dialog;
    }

    private void clearFields() {
        roomsField.clear();
        areaField.clear();
        floorField.clear();
        addressField.clear();
        priceField.clear();
        balconyCheckBox.setSelected(false);
    }

    private void updateStatus(String message) {
        statusLabel.setText("Статус: " + message);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}