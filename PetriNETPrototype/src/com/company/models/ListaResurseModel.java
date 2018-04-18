package com.company.models;

/**
 * Created by Ovidiu on 3/27/2018.
 */
public class ListaResurseModel {
    private int value;
    private String locationName;

    public ListaResurseModel(int value, String locationName) {
        this.value = value;
        this.locationName = locationName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
