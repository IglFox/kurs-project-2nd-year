package edu.com.appartmentapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для управления базой данных квартир
 * Реализует все требуемые операции: CRUD, поиск, сохранение/загрузка
 */
public class Database {
    private CustomContainer<Apartment> apartments;  // Основной контейнер данных
    private int nextId;                             // Счетчик для генерации ID

    public Database() {
        apartments = new CustomContainer<>();
        nextId = 1;  // ID начинаются с 1
    }

    // Создание новой записи
    public void addApartment(Apartment apartment) {
        apartment.setId(nextId++);
        apartments.add(apartment);
    }

    // Удаление записи по индексу
    public void removeApartment(int index) {
        apartments.remove(index);
    }

    // Получение записи по индексу
    public Apartment getApartment(int index) {
        return apartments.get(index);
    }

    // Обновление записи
    public void updateApartment(int index, Apartment apartment) {
        // Сохраняем старый ID
        apartment.setId(apartments.get(index).getId());
        // Удаляем старую и добавляем новую
        apartments.remove(index);
        apartments.add(apartment);
    }

    // Получение всех записей
    public CustomContainer<Apartment> getAllApartments() {
        return apartments;
    }

    // Поиск по количеству комнат
    public CustomContainer<Apartment> searchByRooms(int rooms) {
        CustomContainer<Apartment> result = new CustomContainer<>();
        Iterator<Apartment> it = apartments.iterator();
        while (it.hasNext()) {
            Apartment apt = it.next();
            if (apt.getNumberOfRooms() == rooms) {
                result.add(apt);
            }
        }
        return result;
    }

    // Поиск по диапазону площади
    public CustomContainer<Apartment> searchByArea(double minArea, double maxArea) {
        CustomContainer<Apartment> result = new CustomContainer<>();
        Iterator<Apartment> it = apartments.iterator();
        while (it.hasNext()) {
            Apartment apt = it.next();
            if (apt.getArea() >= minArea && apt.getArea() <= maxArea) {
                result.add(apt);
            }
        }
        return result;
    }

    // Объединение двух баз данных
    public void mergeDatabase(Database other) {
        Iterator<Apartment> it = other.apartments.iterator();
        while (it.hasNext()) {
            Apartment apt = it.next();
            apt.setId(nextId++);  // Присваиваем новый ID
            apartments.add(apt);
        }
    }

    // Сохранение в файл (JSON формат)
    public void saveToFile(String filename) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()  // Красивый формат JSON
                .create();

        // Преобразуем CustomContainer в List для сериализации
        List<Apartment> list = new ArrayList<>();
        Iterator<Apartment> it = apartments.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(list, writer);
        }
    }

    // Загрузка из файла
    public void loadFromFile(String filename) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            Type listType = new TypeToken<ArrayList<Apartment>>(){}.getType();
            List<Apartment> list = gson.fromJson(reader, listType);

            // Очищаем текущую базу
            apartments.clear();

            // Загружаем данные
            for (Apartment apt : list) {
                if (apt.getId() >= nextId) {
                    nextId = apt.getId() + 1;
                }
                apartments.add(apt);
            }
        }
    }

    // Получение размера базы
    public int getSize() {
        return apartments.size();
    }
}