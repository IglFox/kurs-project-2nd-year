package edu.university.kursproject2ndyear.apartmentdb.model;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private String street;
    private String houseNumber;
    private int building;

    public Address(String street, String houseNumber, int building) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.building = building;
    }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getHouseNumber() { return houseNumber; }
    public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }

    public int getBuilding() { return building; }
    public void setBuilding(int building) { this.building = building; }

    @Override
    public String toString() {
        return building > 0 ?
                String.format("ул. %s, д. %s, корп. %d", street, houseNumber, building) :
                String.format("ул. %s, д. %s", street, houseNumber);
    }
}
