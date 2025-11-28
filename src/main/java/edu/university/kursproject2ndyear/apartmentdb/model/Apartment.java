package edu.university.kursproject2ndyear.apartmentdb.model;

import java.io.Serializable;
import java.util.Objects;

public class Apartment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int rooms;
    private double area;
    private double price;
    private String district;
    private Address address;
    private boolean hasBalcony;
    private int floor;

    public Apartment(int id, int rooms, double area, double price,
                     String district, Address address, boolean hasBalcony, int floor) {
        this.id = id;
        this.rooms = rooms;
        this.area = area;
        this.price = price;
        this.district = district;
        this.address = address;
        this.hasBalcony = hasBalcony;
        this.floor = floor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRooms() { return rooms; }
    public void setRooms(int rooms) { this.rooms = rooms; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public boolean hasBalcony() { return hasBalcony; }
    public void setHasBalcony(boolean hasBalcony) { this.hasBalcony = hasBalcony; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Квартира #%d: %d-комн., %.1f м², %.2f руб., %s",
                id, rooms, area, price, district);
    }
}