package com.example.seniorproject;
import java.io.Serializable;
import java.util.Map;

public class plant implements Serializable {
    private String name;
    private String wateringFrequency;
    private String soilType;
    private String picture;

    public plant(String name, String wateringFrequency, String soilType, String picture) {
        this.name = name;
        this.wateringFrequency = wateringFrequency;
        this.soilType = soilType;
        this.picture = picture;
    }

    public plant(Map map){

        this.name = (String) map.get("name");
        this.wateringFrequency = (String) map.get("wateringFrequency");
        this.soilType = (String) map.get("soilType");
        this.picture = (String) map.get("picture");

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }


    public String getWateringFrequency() {
        return wateringFrequency;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }
}
