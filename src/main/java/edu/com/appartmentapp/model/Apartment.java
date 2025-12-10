package edu.com.appartmentapp.model;

import java.io.Serializable;

/**
 * Класс, характеризующий квартиру (количество комнат, площадь и т.д.)
 * Соответствует 15 варианту задания
 */
public class Apartment implements Serializable {
    private int id;                 // Уникальный идентификатор
    private int numberOfRooms;      // Количество комнат
    private double area;            // Площадь в м²
    private int floor;              // Этаж
    private String address;         // Адрес
    private double price;           // Цена
    private boolean hasBalcony;     // Наличие балкона

    public Apartment() {}

    public Apartment(int id, int numberOfRooms, double area, int floor,
                     String address, double price, boolean hasBalcony) {
        this.id = id;
        this.numberOfRooms = numberOfRooms;
        this.area = area;
        this.floor = floor;
        this.address = address;
        this.price = price;
        this.hasBalcony = hasBalcony;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumberOfRooms() { return numberOfRooms; }
    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isHasBalcony() { return hasBalcony; }
    public void setHasBalcony(boolean hasBalcony) { this.hasBalcony = hasBalcony; }

    @Override
    public String toString() {
        return String.format("Квартира #%d: %d-комн., %.1f м², %d этаж, %s, %.2f ₽, Балкон: %s",
                id, numberOfRooms, area, floor, address, price, hasBalcony ? "да" : "нет");
    }
}